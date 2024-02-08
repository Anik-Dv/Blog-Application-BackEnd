package com.anikdv.blog.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anikdv.blog.app.configurations.ModelMapperConfiguration;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.JwtAuthRequest;
import com.anikdv.blog.app.payloads.JwtAuthResponse;
import com.anikdv.blog.app.payloads.UserDto;
import com.anikdv.blog.app.security.JwtUtils;
import com.anikdv.blog.app.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * This is User Authentication Controller
 *
 * @author anikdv
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapperConfiguration mapperConfiguration;

	/**
	 * This Method For User Login
	 *
	 * @param authRequest
	 * @return user authorities
	 */
	@Tag(name = "Authentication User", description = "POST Methods of Login & Signin APIs")
	@Operation(summary = "POST Method User Authentication Login", description = "Post Method User Authentication. The Response is an Access Token.")
	@PostMapping("/login")
	public ResponseEntity<?> loginHandler(final @Valid @RequestBody JwtAuthRequest authRequest) {
		final String METHOD_NAME = "loginHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			this.doAuthentication(authRequest.getUsername(), authRequest.getPassword());
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
			//logger.info("user details {} :", userDetails);
			String token = this.jwtUtils.generateToken(userDetails);
			// setting jwt response
			JwtAuthResponse response = new JwtAuthResponse();
			// set current user in response
			UserDto currentUser = this.mapperConfiguration.modelMapper().map((User)userDetails, UserDto.class);
			response.setToken(token);
			response.setCurrentUser(currentUser);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse("Invalid username or password!", false));
		}
	}

	/**
	 * This Method For User Registration
	 *
	 * @param userDto
	 * @return user authorities
	 */
	@Tag(name = "Authentication User", description = "POST Methods of Signin APIs")
	@Operation(summary = "POST Method User Register", description = "Post Method User Register. The Response is Registered User Object and Other Details.")
	@PostMapping("/signin")
	public ResponseEntity<?> registrationHandler(final @Valid @RequestBody UserDto userDto) {
		final String METHOD_NAME = "registrationHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			if(userDto.getPassword().equals(userDto.getComfirmPassword())) {
				logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
				UserDto registeredUser = this.userService.registrationUser(userDto);
				 return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You Password & Comfirm Password Is Not Matched!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse("Invalid User Credentials!", false));
		}
	}

	/* This Method For Authenticate User */
	private void doAuthentication(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.manager.authenticate(authenticationToken);
		} catch (BadCredentialsException ex) {
			logger.error("Invalid Username or Password!");
			throw new BadCredentialsException("Invalid Username or Password!");
		}
	}

}
