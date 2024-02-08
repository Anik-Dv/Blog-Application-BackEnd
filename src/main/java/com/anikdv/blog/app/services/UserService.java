package com.anikdv.blog.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.anikdv.blog.app.payloads.UserDto;

/**
 * @author AnikDv
 */
public interface UserService {

	/**
	 * This Method For Registration User
	 *
	 * @param userDto
	 * @return an new registered user
	 */
	@CacheEvict(value = "users", allEntries = true)
	UserDto registrationUser(final UserDto userDto);

	/**
	 * This Method For Create User
	 *
	 * @param user
	 * @return created user
	 */
	@CacheEvict(value = "users", allEntries = true)
	UserDto createUser(final UserDto user);

	/**
	 * This Method For Update User
	 *
	 * @param user
	 * @param userId
	 * @return updated user
	 */
	@CacheEvict(value = "users", allEntries = true, key = "#userId")
	UserDto updateUserAvater(final UserDto user, Integer userId);


	/**
	 * This Method For Update User
	 *
	 * @param user
	 * @param userId
	 * @return updated user
	 * @throws Exception
	 */
	@CacheEvict(value = "users", allEntries = true)
	UserDto updateUserDetails(final UserDto user, Integer userId) throws Exception;



	/**
	 * This Method For Get Single User
	 *
	 * @param userId
	 * @return single user
	 */
	@Cacheable(value = "users", key = "#userId")
	UserDto getUserById(final Integer userId);

	/**
	 * This Method For getting all User
	 *
	 * @param user
	 * @return All users
	 */
	@Cacheable(value = "users")
	List<UserDto> getAllUsers();

	/**
	 * This Method For Delete User
	 *
	 * @param userId
	 * @return true/false
	 */
	@CacheEvict(cacheNames = "users", key = "#userId", beforeInvocation = true)
	boolean deleteUser(final Integer userId);

	/**
	 * @param email
	 * @return user
	 */
	@Cacheable(value = "users", key = "#email")
	Optional<UserDto> findByEmail(final String email);

}
