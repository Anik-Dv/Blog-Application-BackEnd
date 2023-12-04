package com.anikdv.blog.app.payloads;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class is DTO (Data Transfer Object)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto implements Serializable {

	private static final long serialVersionUID = 6082824759048463498L;

	private Integer userId;

	@NotBlank
	@Size(min = 4, max = 26, message = "You Name Is Must be Minimum 4 Chars and Maximum is 26 Chars!")
	private String name;
	@NotBlank
	private String role;
	@NotBlank
	@Size(min = 16, max = 900, message = "About is Must be Minimum 16 Chars and Maximum is 900 Chars!")
	private String about;
	@Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email Address is Not Valid!")
	@NotBlank
	private String email;
	@NotBlank
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Minimum eight characters, at least one upper case letter, one number and one special character")
	@Size(min = 8, max = 20)
	private String password;
	private String image_url;
	private String status;
	private LocalDateTime createDate;

}
