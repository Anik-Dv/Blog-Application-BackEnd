package com.anikdv.blog.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is User Entity
 *
 * @author AnikDV
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 6225090273384677163L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_Id")
	private int userId;
	@Column(name = "User_Name")
	private String name;
	@Column(name = "User_Role")
	private String role;
	@Column(length = 900)
	private String about;
	@Column(name = "Email_ID", nullable = false, unique = true)
	private String email;
	private String password;
	private String image_url;
	private String status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Comments> comments = new HashSet<>();

}
