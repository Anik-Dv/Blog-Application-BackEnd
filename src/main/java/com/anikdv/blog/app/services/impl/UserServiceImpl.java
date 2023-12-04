package com.anikdv.blog.app.services.impl;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.repositories.UserRepository;
import com.anikdv.blog.app.services.UserService;

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
	private ModelMapperConfiguration modelMapperConfiguration;

	@Override
	public UserDto getUserById(final Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER ", "ID ", userId));

		return this.EntityToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// System.out.println("Invoked : getAllUsers Method");
		List<User> users = this.userRepository.findAll();
		List<UserDto> allUserDto = users.stream().map(user -> this.EntityToDto(user)).collect(Collectors.toList());
		return allUserDto;
	}

	@Override
	public UserDto createUser(final UserDto userDto) {
		User user = null;
		try {
			user = this.DtoToEntity(userDto);
			Optional<User> userDetails = this.userRepository.findByEmail(user.getEmail());
			if (userDetails.isPresent()) {
				throw new InternalError("This Email ID '" + userDetails.get().getEmail() + "' Already Exists!");
			}
			user.setCreateDate(LocalDateTime.now());
			User savedUser = this.userRepository.save(user);
			return this.EntityToDto(savedUser);

		} catch (Exception e) {
			e.getMessage();
		}
		return this.EntityToDto(user);
	}

	@Override
	public UserDto updateUser(final UserDto userDto, Integer userId) {

		// search user form resource
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("USER", "ID ", userId));

		// set user new informations
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setStatus(userDto.getStatus());
		user.setImage_url(userDto.getImage_url());
		user.setPassword(userDto.getPassword());

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

}
