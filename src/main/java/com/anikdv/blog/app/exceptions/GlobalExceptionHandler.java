/**
 * 
 */
package com.anikdv.blog.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anikdv.blog.app.payloads.ApiResponse;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @param notFoundException
	 * @return Not_Found Status Code and ApiResponse Object
	 * @info when any resource not found exception throw rest controller then run
	 *       this resourceNotFoundExceptiuonHandler Method.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptiuonHandler(ResourceNotFoundException notFoundException) {
		String message = notFoundException.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
	}
	
	/**
	 * @param e
	 * @return BadRequest Status and throws MethodArgumentNotValidException 
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> argumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> result = new HashMap<>();
		
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String errorField = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();			
			result.put(errorField, errorMessage);			
		});
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
