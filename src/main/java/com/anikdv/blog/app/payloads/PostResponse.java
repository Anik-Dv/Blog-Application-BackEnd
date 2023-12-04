package com.anikdv.blog.app.payloads;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author AnikDV
 * @apiNote This is a Post Response Class
 */
@NoArgsConstructor
@Getter
@Setter
public class PostResponse implements Serializable {

	private static final long serialVersionUID = -2722704703311341995L;

	private long pageNumber;
	private long pageContentSize;
	private long totalContent;
	private long totalPages;
	private boolean isLastPage;
	private List<PostDto> content;
}