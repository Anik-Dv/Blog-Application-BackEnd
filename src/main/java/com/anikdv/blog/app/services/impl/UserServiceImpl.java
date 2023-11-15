package com.anikdv.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public UserDto getUserById(final Integer userId) {
		User user = null;
		try {
			user = this.userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("USER ", "ID ", userId));
			return this.EntityToDto(user);
		} catch (ResourceNotFoundException e) {
			e.getMessage();
		}
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
		User user = null;
		try {
			user = this.DtoToEntity(userDto);
			User savedUser = this.userRepository.save(user);
			return this.EntityToDto(savedUser);

		} catch (Exception e) {
			e.getMessage();
		}
		return this.EntityToDto(user);
	}

	@Override
	public UserDto updateUser(final UserDto userDto, Integer userId) {

		// initial user is null;
		User user = null;

		try {
			// search user form resource
			user = this.userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("USER ", "ID ", userId));

			// set user new informations
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setAbout(userDto.getAbout());
			user.setStatus(userDto.getStatus());
			user.setImage_url(userDto.getImage_url());
			user.setPassword(userDto.getPassword());

			// update the user
			User updatedUser = this.userRepository.save(user);
			UserDto User_Dto = this.EntityToDto(updatedUser);
			return User_Dto;
		} catch (ResourceNotFoundException e) {
			e.getMessage();
		}
		return this.EntityToDto(user);
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
	 * Convert UserDTO To UserEntity
	 * 
	 * @param userDto
	 * @return userEntity
	 */
	public User DtoToEntity(final UserDto userDto) {
		User user = new User(userDto.getUserId(), userDto.getName(), userDto.getRole(), userDto.getAbout(),
				userDto.getEmail(), userDto.getPassword(), userDto.getImage_url(), userDto.getStatus());
		return user;
	}

	/**
	 * Convert UserEntity To UserDTO
	 * 
	 * @param user
	 * @return userDto
	 */
	public UserDto EntityToDto(final User user) {
		UserDto userDto = new UserDto(user.getUserId(), user.getName(), user.getRole(), user.getAbout(),
				user.getEmail(), user.getPassword(), user.getImage_url(), user.getStatus());
		return userDto;
	}

}
