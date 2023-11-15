/**
 * 
 */
package com.anikdv.blog.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ApiResponse> resourceNotFoundExceptiuonHandler(ResourceNotFoundException notFoundException) {
		String message = notFoundException.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
	}

}
