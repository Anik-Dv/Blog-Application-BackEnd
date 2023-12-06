package com.anikdv.blog.app.services;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.anikdv.blog.app.payloads.CommentsDto;

/**
 * This is Comment Service Declaration
 * 
 * @author anikdv
 */
public interface CommentService {

	/**
	 * This Method For Created Comment
	 * 
	 * @param comment
	 * @param user_id
	 * @param post_id
	 * @return created comment
	 */
	@CacheEvict(value = "comment", key = "#user_id + #post_id", allEntries = true)
	CommentsDto createComment(final CommentsDto comment, final Integer user_id, final Integer post_id);

	/**
	 * This Method For Update Comment
	 * 
	 * @param comment
	 * @param comment_id
	 * @param user_id
	 * @param post_id
	 * @return updated comment
	 */
	@CacheEvict(value = "comment", allEntries = true)
	CommentsDto updateComment(final CommentsDto comment, final Integer comment_id, final Integer user_id,
			final Integer post_id);

	/**
	 * This Method For Delete Comment
	 * 
	 * @param comment_id
	 * @param user_id
	 * @param post_id
	 * @return True/False if delete comment
	 */
	@CacheEvict(cacheNames = "comment", key = "#comment_id + #user_id + #post_id", allEntries = true)
	boolean deleteComment(final Integer comment_id, final Integer user_id, final Integer post_id);

	/**
	 * This Method For Fetch All Comments
	 * 
	 * @return List of Comments || NOT NULL
	 */
	@Cacheable(value = "comment")
	List<CommentsDto> getAllComments();

	/**
	 * This Method For Fetch Single Comment
	 * 
	 * @param comment_id
	 * @return Single Comment | NOT NULL
	 */
	@Cacheable(value = "comment", key = "#comment_id")
	CommentsDto getComment(final Integer comment_id);

	/**
	 * @param post_id
	 * @return all comments of post
	 */
	@Cacheable(value = "comment", key = "#post_id")
	List<CommentsDto> getAllCommentsByPost(final Integer post_id);

	/**
	 * @param user_id
	 * @return all comments of user
	 */
	@Cacheable(value = "comment", key = "#user_id")
	List<CommentsDto> getAllCommentsByUser(final Integer user_id);

}
