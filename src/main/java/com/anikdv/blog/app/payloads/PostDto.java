package com.anikdv.blog.app.payloads;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PostDto implements Serializable {
	private static final long serialVersionUID = 6340156754117897222L;

	private Integer postId;

	@NotBlank
	@Size(min = 6, max = 80, message = "Post Title Must be Minimum of 6 Chars and Maximum is 80 Chars!")
	private String title;

	@NotBlank
	@Size(min = 6, max = 2600, message = "Post Content Must be Minimum of 4 Chars and Maximum is 2600 Chars!")
	private String content;

	@Size(min = 3, max = 25, message = "Post Tag Must be Minimum of 3 Chars and Maximum is 25 Chars!")
	private String tag;
	private String imageName;
	private LocalDateTime createDate;
	@NotBlank
	private CategoryDto category;
	@NotBlank
	private UserDto user;

	@NotBlank
	private Set<CommentsDto> comments = new HashSet<>();

}
