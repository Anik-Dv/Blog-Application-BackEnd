package com.anikdv.blog.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity of Comments
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;

	@Column(name = "commentDate", nullable = false)
	private LocalDateTime createDate;

	@Column(length = 150, nullable = false)
	private String Content;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false, name = "user_Id")
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false, name = "post_Id")
	private Post post;
}
