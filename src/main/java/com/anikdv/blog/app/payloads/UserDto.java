package com.anikdv.blog.app.payloads;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This Model Class is DTO (Data Transfer Object)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
	private static final long serialVersionUID = 6082824759048463498L;

	private Integer userid;
	@NotBlank
	@Size(min = 4, max = 26, message = "You Name Is Must be Minimum 4 Chars and Maximum is 26 Chars!")
	private String name;

	@Size(min = 16, max = 900, message = "About is Must be Minimum 16 Chars and Maximum is 900 Chars!")
	private String about;
	@Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email Address is Not Valid!")
	@NotBlank
	private String email;
	@NotBlank
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Minimum eight characters, at least one upper case letter, one number and one special character")
	@Size(min = 8, max = 20)
	private String password;
	@NotBlank
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Minimum eight characters, at least one upper case letter, one number and one special character")
	@Size(min = 8, max = 20)
	private String comfirmPassword;
	private String image_Name;
	private String status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}

	@JsonIgnore
	public String getComfirmPassword() {
		return this.comfirmPassword;
	}

	@JsonProperty
	public void setComfirmPassword(String comfirmPassword) {
		this.comfirmPassword = comfirmPassword;
	}
}
