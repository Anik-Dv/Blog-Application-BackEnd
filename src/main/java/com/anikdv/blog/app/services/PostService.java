package com.anikdv.blog.app.services;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

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
	 * @param posts
	 * @param categoryId
	 * @param userId
	 * @param commentId
	 * @param path
	 * @param file
	 * @return created post
	 * @throws Exception
	 */
	@CacheEvict(value = "posts", key = "#userId + #categoryId", allEntries = true)
	PostDto createPost(final String posts, final Integer userId, final Integer categoryId, final String path,
			final MultipartFile file) throws Exception;


	/**
	 * For Update Post
	 *
	 * @param posts
	 * @param postId
	 * @return Updated Post
	 * @throws ResourceNotFoundException
	 * @throws Exception
	 */
	@CacheEvict(value = "posts", allEntries = true)
	PostDto updatePost(final PostDto posts, final Integer postId, final Integer userId) throws ResourceNotFoundException, Exception;

	/**
	 * @param postId
	 * @param path
	 * @return if post deleted then return true/false
	 */
	@CacheEvict(cacheNames = "posts", key = "#postId", allEntries = true)
	boolean deletePost(final Integer postId, final String path);

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param asc
	 * @return All Posts
	 * @throws ResourceNotFoundException
	 */
	@Cacheable(value = "posts")
	PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String asc)
			throws ResourceNotFoundException;

	/**
	 * @param postId
	 * @return post
	 */
	@Cacheable(value = "posts", key = "#postId")
	PostDto getPostById(final Integer postId);

	/**
	 * @param userId
	 * @return post
	 */
	@Cacheable(value = "posts", key = "#userId")
	Set<PostDto> getPostByUser(final Integer userId);

	/**
	 * @param categoryId
	 * @return post
	 */
	@Cacheable(value = "posts", key = "#categoryId")
	Set<PostDto> getPostByCategory(final Integer categoryId);

	/**
	 * @param keyword
	 * @return post
	 */
	@Cacheable(cacheNames = "posts", key = "#keyword")
	List<PostDto> searchPostByKeyword(final String keyword);

}
