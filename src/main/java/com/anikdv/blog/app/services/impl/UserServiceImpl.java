package com.anikdv.blog.app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.Comments;
import com.anikdv.blog.app.entities.Role;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.repositories.CommentsRepository;
import com.anikdv.blog.app.repositories.RoleRepository;
import com.anikdv.blog.app.repositories.UserRepository;
import com.anikdv.blog.app.services.UserService;
import com.anikdv.blog.app.utils.AppConstants;

/**
 * @info This Class is implements userService interface
 * @author AnikDV
 * @category Service
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentsRepository commentsRepository;

	@Autowired
	private ModelMapperConfiguration modelMapperConfiguration;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto getUserById(final Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER ", "ID ", userId));

		return this.EntityToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> allUserDto = users.stream().map(user -> this.EntityToDto(user)).collect(Collectors.toList());
		return allUserDto;
	}

	@Override
	public UserDto createUser(final UserDto userDto) {
		User user = this.DtoToEntity(userDto);
		System.out.println(user.toString());

		Set<Comments> comments = this.commentsRepository.findByUser(user);
		Optional<User> userDetails = this.userRepository.findByEmail(user.getEmail());

		if (userDetails.isPresent()) {
			throw new InternalError("This Email ID '" + userDetails.get().getEmail() + "' Already Exists!");
		}
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setCreateDate(LocalDateTime.now());
		user.setComments(comments);
		User savedUser = this.userRepository.save(user);
		System.out.println(user);
		return this.EntityToDto(savedUser);
	}

	@Override
	public UserDto updateUser(final UserDto userDto, Integer userId) {

		// search user form resource
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID ", userId));

		// set user new informations
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setStatus(userDto.getStatus());
		user.setImage_url(userDto.getImage_url());

		// if user put something then update
		if (userDto.getPassword() != null || !userDto.getPassword().isEmpty()) {
			user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		}
		// if user Given something For update
		if (userDto.getEmail() != null || !userDto.getEmail().isEmpty()) {
			user.setEmail(userDto.getEmail());
		}

		// otherwise keep as it as
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setEmail(user.getEmail());

		// update the user
		User updatedUser = this.userRepository.save(user);
		return this.EntityToDto(updatedUser);
	}

	@Override
	public boolean deleteUser(final Integer userId) {
		boolean flag = false;
		try {
			User user = this.userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("USER ", "ID ", userId));
			this.userRepository.delete(user);
			flag = true;
			return flag;
		} catch (ResourceNotFoundException e) {
			e.getMessage();
			return flag;
		}
	}

	/**
	 * DtoToEntity convert object an DTO To Entity
	 *
	 * @param userDto object
	 * @return fully mapped instance of {@code destinationType}
	 */
	public User DtoToEntity(final UserDto userDto) {
		return this.modelMapperConfiguration.modelMapper().map(userDto, User.class);
	}

	/**
	 * EntityToDto {@code source} convert object an Entity To DTO
	 *
	 * @param user object
	 * @return fully mapped instance of {@code destinationType}
	 */
	public UserDto EntityToDto(final User user) {
		return this.modelMapperConfiguration.modelMapper().map(user, UserDto.class);
	}

	@Override
	public Optional<UserDto> findByEmail(String emailId) {
		Optional<User> userDetails = this.userRepository.findByEmail(emailId);
		Optional<UserDto> userDto = Optional
				.of(this.modelMapperConfiguration.modelMapper().map(userDetails, UserDto.class));
		return userDto;
	}

	@Override
	public UserDto registrationUser(UserDto userDto) {
		User user = this.modelMapperConfiguration.modelMapper().map(userDto, User.class);

		// setting properties
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setCreateDate(LocalDateTime.now());
		// user as normal
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User registeredUser = this.userRepository.save(user);
		return this.modelMapperConfiguration.modelMapper().map(registeredUser, UserDto.class);
	}

}
