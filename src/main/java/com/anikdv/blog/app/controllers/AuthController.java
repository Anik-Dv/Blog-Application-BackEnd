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

import com.anikdv.blog.app.payloads.ApiResponse;
import com.anikdv.blog.app.payloads.JwtAuthRequest;
import com.anikdv.blog.app.payloads.JwtAuthResponse;
import com.anikdv.blog.app.security.JwtUtils;

/**
 * This is User Authentication Controller
 * 
 * @author anikdv
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * This Method For User Login
	 *
	 * @param authRequest
	 * @return user authorities
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginHandler(@RequestBody JwtAuthRequest authRequest) {
		final String METHOD_NAME = "loginHandler";
		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
		try {
			this.doAuthentication(authRequest.getUsername(), authRequest.getPassword());
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
			logger.info("user details {} :", userDetails);
			String token = this.jwtUtils.generateToken(userDetails);
			// setting jwt response
			JwtAuthResponse response = new JwtAuthResponse();
			response.setToken(token);
			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse("Invalid username or password!", false));
		}
	}
//
//	/**
//	 * This Method For User Logout
//	 *
//	 * @param session
//	 * @return Status with Response Method
//	 */
//	@GetMapping("/logout")
//	public ResponseEntity<?> logoutHandler(final HttpSession session) {
//		final String METHOD_NAME = "logoutHandler";
//		logger.info("Method Invoked: " + this.getClass().getName() + ":" + METHOD_NAME);
//		try {
//			//logger.info("User Email Id : {}", user.getEmail());
//			
//			logger.info(":: Logout ::");
//
//	        // invalidate session
//	        session.invalidate();
//
//			logger.info("Response The Method: " + this.getClass().getName() + ":" + METHOD_NAME);
//			return ResponseEntity.status(HttpStatus.OK).body("");
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body(new ApiResponse("Something Went Wrong! Please Try Again!", false));
//		}
//	}

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
