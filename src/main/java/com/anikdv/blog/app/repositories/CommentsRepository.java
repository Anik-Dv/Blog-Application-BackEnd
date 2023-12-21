package com.anikdv.blog.app.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.Comments;
import com.anikdv.blog.app.entities.User;
import com.anikdv.blog.app.payloads.CommentsDto;
import com.anikdv.blog.app.payloads.PostDto;

/**
 * This is Comment Repository
 *
 * @author anikdv
 */
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

	/**
	 * findByUser is custom method for finding user
	 *
	 * @param user
	 * @return comment
	 */
	Set<Comments> findByUser(User user);

	/**
	 * findByUser is custom method for finding user
	 *
	 * @param posts
	 * @return comment
	 */
	Set<CommentsDto> findByPost(PostDto posts);

}
