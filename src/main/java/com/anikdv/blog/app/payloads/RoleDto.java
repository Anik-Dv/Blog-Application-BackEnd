package com.anikdv.blog.app.payloads;

import java.io.Serializable;

import lombok.Data;

/**
 * This is Role DTO
 */
@Data
public class RoleDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String Name;
}
