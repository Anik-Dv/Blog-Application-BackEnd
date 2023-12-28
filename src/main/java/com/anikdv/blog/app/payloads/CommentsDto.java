package com.anikdv.blog.app.payloads;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * This is Comments DTO
 */
@Data
public class CommentsDto implements Serializable {
	private static final long serialVersionUID = -3816259610003470315L;

	private Integer commentId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;

	@NotEmpty(message = "Comment Content Must Not be Empty!")
	@Size(min = 3, max = 130, message = "Content Must be Minimum 3 Chars and Maximum 130 Chars!")
	private String Content;
}
