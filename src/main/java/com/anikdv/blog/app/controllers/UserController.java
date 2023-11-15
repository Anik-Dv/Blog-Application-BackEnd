package com.anikdv.blog.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.services.UserService;

/**
 * User RestController
*/

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	/**
	 * @info This Method call for create new user
	 * @param userDto
	 * @return Status Code
	 */
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDto> createUser(final @RequestBody UserDto userDto) {
		final String METHOD_NAME = "createUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			UserDto createdUser = this.userService.createUser(userDto);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * @info This Method call for Update User
	 * @param userDto
	 * @param userId
	 * @return Status Code
	 */
	@PutMapping(value = "/update/{userId}", consumes = "application/json")
	public ResponseEntity<?> updateUser(final @RequestBody UserDto userDto, final @PathVariable Integer userId) {
		final String METHOD_NAME = "updateUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			if (userId != null || !userDto.getName().isEmpty() || !userDto.getEmail().isEmpty()
					|| !userDto.getAbout().isEmpty() || !userDto.getPassword().isEmpty()) {
				UserDto updatedUser = this.userService.updateUser(userDto, userId);

				logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
				return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
			}

		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User Can't Deleted!", false));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * @param userId
	 * @return Status Code
	 */
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {

		final String METHOD_NAME = "deleteUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			boolean deleteUser = this.userService.deleteUser(userId);
			if (deleteUser) {
				logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User Deleted Successfully", true));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User Not Found!", false));
			}
		} catch (ResourceNotFoundException e) {
			logger.error("User Can't Deleted");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Something Went Wrong!", false));
		}
	}

	/**
	 * @return All Users Resources
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<?>> getAllUsers() {
		final String METHOD_NAME = "getAllUsers";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			List<UserDto> allUsers = this.userService.getAllUsers();
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(allUsers);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * @param userId
	 * @return Single Users Resources
	 */
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> getSingleUser(@PathVariable Integer userId) {
		final String METHOD_NAME = "getSingleUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			UserDto singleUser = this.userService.getUserById(userId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.FOUND).body(singleUser);

		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Invalided User Id!", false));
		}
	}

}
