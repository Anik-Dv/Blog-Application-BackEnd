package com.anikdv.blog.app.payloads;

/**
 * This Model Class is DTO (Data Transfer Object)
 */
public class UserDto {

	private int userId;
	private String name;
	private String role;
	private String about;
	private String email;
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
