package com.anikdv.blog.app.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.Category;
import com.anikdv.blog.app.entities.Post;
import com.anikdv.blog.app.entities.User;

/**
 * This is Post Repository
 *
 * @author AnikDV
 */

public interface PostsRepository extends JpaRepository<Post, Integer> {

	/**
	 * findByCategory is custom method for finding category
	 *
	 * @param category
	 * @return post
	 */
	Set<Post> findByCategory(Category category);

	/**
	 * findByUser is custom method for finding user
	 *
	 * @param user
	 * @return post
	 */
	Set<Post> findByUser(User user);

}
