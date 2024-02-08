package com.anikdv.blog.app.payloads;

import lombok.Data;

/**
 * This Is For Response Authentication Details
 *
 * @author anikdv
 *
 */
@Data
public class JwtAuthResponse {
	private String token;
	private UserDto currentUser;
}
