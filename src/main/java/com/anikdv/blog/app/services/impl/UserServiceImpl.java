package com.anikdv.blog.app.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public UserDto updateUserAvater(final UserDto userDto, Integer userId) {

		// search user form resource
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID ", userId));

		// set user new informations
		user.setImage_Name(userDto.getImage_Name());


		// update the user
		User updatedUser = this.userRepository.save(user);
		return this.EntityToDto(updatedUser);
	}



	@Override
	public UserDto updateUserDetails(final UserDto userDto, Integer userId) throws Exception {

		// search user form resource
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID ", userId));

		// set user new informations
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setStatus(userDto.getStatus());

		if(!userDto.getRoles().isEmpty()) {
			user.setRoles(user.getRoles());
		}

		// user as normal
		Role user_role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user_role.setUser(user);
		user.getRoles().add(user_role);

		// check user new given pws is equals to user already have pws
		// suppose -> password is act like current password. so,
		// and confirm password is act like new password and then save confirm password is both as it is.
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean matches = encoder.matches(userDto.getPassword(), user.getPassword());

		if(matches) { // if true
			// then update the user pws
			// if user put something then update
			if (userDto.getPassword() != null || !userDto.getPassword().isEmpty()) {
				user.setPassword(this.passwordEncoder.encode(userDto.getComfirmPassword()));
			}

			// if user put something then update
			if (userDto.getComfirmPassword() != null || !userDto.getComfirmPassword().isEmpty()) {
				user.setComfirmPassword(this.passwordEncoder.encode(userDto.getComfirmPassword()));
			}
		} else {
			// return throw Exception password not matches
			throw new Exception("Your Current Password Not Matches!");
		}

		// if user Given something For update
		if (userDto.getEmail() != null || !userDto.getEmail().isEmpty()) {
			user.setEmail(userDto.getEmail());
		}

		// otherwise keep as it as
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setComfirmPassword(this.passwordEncoder.encode(userDto.getComfirmPassword()));
		user.setEmail(user.getEmail());

		// update the user
		User updatedUser = this.userRepository.save(user);
		this.roleRepository.save(user_role);
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
	public UserDto registrationUser(UserDto userDto)  {
		User user = this.modelMapperConfiguration.modelMapper().map(userDto, User.class);

		// setting properties
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setComfirmPassword(this.passwordEncoder.encode(userDto.getComfirmPassword()));
		user.setCreateDate(LocalDateTime.now());
		// user as normal
		Role user_role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user_role.setUser(user);
		user.getRoles().add(user_role);

//		Set<Role> roleSet = new HashSet<Role>();
//		roleSet.add(user_role);
//		user.setRoles(roleSet);

//		Role role = new Role();
//		role.setUser(user);


		User registeredUser = this.userRepository.save(user);
		this.roleRepository.save(user_role);

		return this.modelMapperConfiguration.modelMapper().map(registeredUser, UserDto.class);
	}

}
