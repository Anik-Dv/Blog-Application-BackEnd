package com.anikdv.blog.app.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Post implements Serializable {

	private static final long serialVersionUID = 4386383408966200842L;

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
	private LocalDateTime createDate;

	@ManyToOne
	@JoinColumn(nullable = false, name = "category_id")
	@JsonManagedReference
	private Category category;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	@JsonManagedReference
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comments> comments = new HashSet<>();

}
