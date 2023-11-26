package com.anikdv.blog.app.services;

import java.util.List;
import java.util.Set;

import com.anikdv.blog.app.exceptions.ResourceNotFoundException;
import com.anikdv.blog.app.payloads.PostDto;
import com.anikdv.blog.app.payloads.PostResponse;

/**
 * This is Declaration of Post Service
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
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param asc
	 * @return All Posts
	 * @throws ResourceNotFoundException
	 */
	PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String asc)
			throws ResourceNotFoundException;

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
	List<PostDto> searchPostByKeyword(final String keyword);

}