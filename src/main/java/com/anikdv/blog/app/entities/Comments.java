package com.anikdv.blog.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Comments implements Serializable {
	private static final long serialVersionUID = -7492265083609901000L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer comment_id;

	@Column(name = "commentDate", nullable = false)
	private LocalDateTime createDate;

	@Column(length = 150, nullable = false)
	private String Content;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_Id")
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false, name = "post_Id")
	private Post post;
}
