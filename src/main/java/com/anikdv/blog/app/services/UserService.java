package com.anikdv.blog.app.services;

import java.util.List;
import java.util.Optional;

import com.anikdv.blog.app.payloads.UserDto;

/**
 * @author AnikDv
 */
public interface UserService {

	/**
	 * This Method For Create User
	 * @param user
	 * @return created user
	 */
	UserDto createUser(final UserDto user);

	/**
	 * This Method For Update User
	 * @param user
	 * @param userId
	 * @return updated user
	 */
	UserDto updateUser(final UserDto user, Integer userId);

	/**
	 * This Method For Get Single User
	 * @param userId
	 * @return single user
	 */
	UserDto getUserById(final Integer userId);

	/**
	 * This Method For getting all User
	 * @param user
	 * @return All users
	 */
	List<UserDto> getAllUsers();

	/**
	 * This Method For Delete User
	 * @param userId
	 * @return true/false
	 */
	boolean deleteUser(final Integer userId);

	/**
	 * @param emailId
	 * @return user
	 */
	Optional<UserDto> findByEmail(final String emailId);

}
