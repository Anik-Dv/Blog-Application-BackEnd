package com.anikdv.blog.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author AnikDV
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {

	private String message;
	private boolean status;
}
