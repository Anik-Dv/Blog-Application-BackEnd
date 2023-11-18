package com.anikdv.blog.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * This is User Entity
 * 
 * @author AnikDV
 */

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id")
	private int userId;
	@Column(name = "User_Name")
	private String name;
	@Column(name = "User_Role")
	private String role;
	private String about;
	@Column(name = "Email_ID", nullable = false, unique = true)
	private String email;
	private String password;
	private String image_url;
	private String status;

	/**
	 * 
	 */
	public User() {
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
	public User(int userId, String name, String role, String about, String email, String password, String image_url,
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
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return about
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * @param about
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * @param image_url
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", role=" + role + ", about=" + about + ", email=" + email
				+ ", password=" + password + ", image_url=" + image_url + ", status=" + status + "]";
	}

}
