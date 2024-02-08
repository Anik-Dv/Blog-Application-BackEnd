package com.anikdv.blog.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.services.FileService;
import com.anikdv.blog.app.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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


	@Autowired
	private FileService fileService;

	@Value("${file.upload-dir}")
	private String path;


	/**
	 * @info This Method for create new user
	 * @param userDto
	 * @return Status with created post | NOT NULL
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Create New User",
    description = "Create an New User. The response is Create New User object with id, name, and other details.")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<?> createUser(final @Valid @RequestBody UserDto userDto) {
		final String METHOD_NAME = "createUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			UserDto createdUser = this.userService.createUser(userDto);
			logger.info("user id is : {} " + userDto.getUserid());
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(e.getMessage() + "| Invalid Request!", false));
		}
	}

	/**
	 * @info This Method For Update User
	 * @param userDto
	 * @param userId
	 * @return Status with updated user | NOT NULL
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Update an User",
    description = "Update An Existing Users. The response is updated User object with id, name, Email And Other Details.")
//	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@PutMapping(value = "/update/{userId}", consumes = "application/json")
	public ResponseEntity<?> updateUser(final @Valid @RequestBody UserDto userDto, final @PathVariable Integer userId) {
		final String METHOD_NAME = "updateUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {

			if (userDto == null || userId == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("User or ID must not be Empty!", false));
			}

			UserDto updatedUser;
			try {
				updatedUser = this.userService.updateUserDetails(userDto, userId);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse(e.getMessage(), false));
			}
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
	 *
	 * @param userId
	 * @return Status with delete user True/False | NOT NULL
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Delete an User",
    description = "Delete an existing User. The response is Message if User is Deleted Success or Not.")
	@PreAuthorize("hasRole('ADMIN')")
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
	 * This Method For Get All Users
	 *
	 * @return All Users Resources | NOT NULL
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Get All Users",
    description = "Get All existing Users. The response is List of All Users object with id, name, email and other details.")
	@GetMapping(value = "/")
	public ResponseEntity<List<?>> getAllUsers() {
		final String METHOD_NAME = "getAllUsers";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			List<UserDto> allUsers = this.userService.getAllUsers();
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(allUsers);
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
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Get Single User",
    description = "Get Single User an existing Users. The response is User object with id, name, about, email with other details.")
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> getSingleUser(@PathVariable Integer userId) {
		final String METHOD_NAME = "getSingleUser";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);

		try {
			UserDto singleUser = this.userService.getUserById(userId);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(singleUser);

		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage() + " | Invalided User Id!", false));
		}
	}



	/**
	 * Upload User Avater
	 *
	 * @param file
	 * @param userId
	 * @return status with file
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Upload User Avater",
    description = "Upload User Avater. The Response is Uploaded Images of User.")
	@PostMapping(value = "/upload/{userId}")
	public ResponseEntity<?> uploadUserAvater(final @PathVariable Integer userId,
			@RequestParam("file") MultipartFile file) {
		final String METHOD_NAME = "uploadUserAvater";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		// find previous user
		UserDto user = this.userService.getUserById(userId);

		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("File Is Must Not Empty!", false));
		}

		// upload the file
		String fileName;
		try {
			fileName = this.fileService.fileUpoad(path, file);
			// set new property
			user.setImage_Name(fileName);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("User Profile Image is Not Uploaded!", false));
		}

		// then this update post
		UserDto userDto = this.userService.updateUserAvater(user, userId);

		logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}



	/**
	 * Fetch User Avater
	 *
	 * @param fileName
	 * @param response
	 * @return file
	 */
	@Tag(name = "User API", description = "All Methods of User APIs")
	@Operation(summary = "Get Post Images",
    description = "Get User Avater. The Response is Get Images of User.")
	@GetMapping(value = "/file/{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> getFileResource(final @PathVariable String fileName, HttpServletResponse response) {
		final String METHOD_NAME = "getFileResource";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			InputStream fileResource = this.fileService.getFileResource(path, fileName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(fileResource, response.getOutputStream());
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.contentType(MediaType.valueOf(response.getContentType())).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Avater Is Not Found!");
		}
	}






}
