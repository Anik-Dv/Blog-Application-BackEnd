package com.anikdv.blog.app.payloads;

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
public class PostResponse {
	private long pageNumber;
	private long pageContentSize;
	private long totalElements;
	private long totalPages;
	private List<PostDto> content;
	private boolean isLastPage;
}