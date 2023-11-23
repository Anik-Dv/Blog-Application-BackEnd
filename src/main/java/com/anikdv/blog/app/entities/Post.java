package com.anikdv.blog.app.entities;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is Post Entity
 *
 * @author AnikDV
 */

@Entity
@Table(name = "Posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@Column(nullable = false, length = 80)
	private String title;
	@Column(nullable = false, length = 2600)
	private String content;
	private String tag;
	private String imageName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private ZonedDateTime createDate;

	@ManyToOne
	@JoinColumn(nullable = false, name = "category_ID")
	@JsonManagedReference
	private Category category;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_ID")
	@JsonManagedReference
	private User user;

}
