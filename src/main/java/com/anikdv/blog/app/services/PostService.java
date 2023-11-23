package com.anikdv.blog.app.services;

import java.util.List;
import java.util.Set;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.PostDto;

/**
 * This is Post Service
 *
 * @author AnikDV
 */
public interface PostService {

	/**
	 * For Create Post
	 *
	 * @param postdto
	 * @param categoryId
	 * @param userId
	 * @return created post
	 */
	PostDto createPost(final PostDto postdto, final Integer userId, final Integer categoryId);

	/**
	 * For Update Post
	 *
	 * @param postDto
	 * @param postId
	 * @return Updated Post
	 * @throws ResourceNotFoundException
	 */
	PostDto updatePost(final PostDto postDto, final Integer postId) throws ResourceNotFoundException;

	/**
	 * @param postId
	 * @return if post deleted then return true/false
	 */
	boolean deletePost(final Integer postId);

	/**
	 * @return All Posts
	 * @throws ResourceNotFoundException
	 */
	List<PostDto> getPosts() throws ResourceNotFoundException;

	/**
	 * @param postId
	 * @return post
	 */
	PostDto getPostById(final Integer postId);

	/**
	 * @param userId
	 * @return post
	 */
	Set<PostDto> getPostByUser(final Integer userId);

	/**
	 * @param categoryId
	 * @return post
	 */
	Set<PostDto> getPostByCategory(final Integer categoryId);

	/**
	 * @param keyword
	 * @return post
	 */
	PostDto searchPostByKeyword(final String keyword);

}
