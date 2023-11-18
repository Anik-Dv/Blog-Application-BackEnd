package com.anikdv.blog.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * This Model Class is DTO (Data Transfer Object)
 */
public class UserDto {

	private int userId;

	@NotBlank
	@Size(min = 4, max = 26, message = "You Name Is Must be Minimum 4 Chars and Maximum is 26 Chars!")
	private String name;
	@NotBlank
	private String role;
	@NotBlank
	@Size(min = 16, max = 2500, message = "About is Must be Minimum 12 Chars and Maximum is 2500 Chars!")
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

	/**
	 * 
	 */
	public UserDto() {
		super();
	}

	/**
	 * @param userId
	 * @param name
	 * @param role
	 * @param about
	 * @param email
	 * @param password
	 * @param image_url
	 * @param status
	 */
	public UserDto(int userId, String name, String role, String about, String email, String password, String image_url,
			String status) {
		super();
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.about = about;
		this.email = email;
		this.password = password;
		this.image_url = image_url;
		this.status = status;
	}
	
	/**
	 * @param name
	 * @param role
	 * @param about
	 * @param email
	 * @param password
	 * @param image_url
	 * @param status
	 */
	public UserDto(String name, String role, String about, String email, String password, String image_url,
			String status) {
		super();
		this.name = name;
		this.role = role;
		this.about = about;
		this.email = email;
		this.password = password;
		this.image_url = image_url;
		this.status = status;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * @param about the about to set
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", name=" + name + ", role=" + role + ", about=" + about + ", email="
				+ email + ", password=" + password + ", image_url=" + image_url + ", status=" + status + "]";
	}

}
