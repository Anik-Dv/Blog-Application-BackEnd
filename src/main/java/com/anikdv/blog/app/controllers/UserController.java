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

import jakarta.validation.Valid;

/**
 * @apiNote This is User REST API Controller
 * @author AnikDV
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * @info This Method for create new user
	 * @param userDto
	 * @return Status with created post | NOT NULL
	 */
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDto> createUser(final @Valid @RequestBody UserDto userDto) {
		final String METHOD_NAME = "createUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			UserDto createdUser = this.userService.createUser(userDto);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * @info This Method For Update User
	 * @param userDto
	 * @param userId
	 * @return Status with updated user | NOT NULL
	 */
	@PutMapping(value = "/update/{userId}", consumes = "application/json")
	public ResponseEntity<?> updateUser(final @Valid @RequestBody UserDto userDto, final @PathVariable Integer userId) {
		final String METHOD_NAME = "updateUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {

			if (userDto == null || userId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("User or ID must not be Empty!", false));
			}

			UserDto updatedUser = this.userService.updateUser(userDto, userId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(updatedUser);

		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse("User ID " + userId + " does not exist.", false));
		}
	}

	/**
	 * This Method For Delete User
	 * @param userId
	 * @return Status with delete user True/False | NOT NULL
	 */
	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<?> deleteUser(final @PathVariable Integer userId) {

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
	 * This Method For Delete User
	 * @return All Users Resources | NOT NULL
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
	 * This Method For Get Single User
	 *
	 * @param userId
	 * @return Single Users Resources | NOT NULL
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage() + " | Invalided User Id!", false));
		}
	}

}
