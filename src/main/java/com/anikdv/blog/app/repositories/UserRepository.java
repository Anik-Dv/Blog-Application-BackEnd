package com.anikdv.blog.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.User;

/**
 * @author AnikDv User Repository
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	/**
	 * @param email
	 * @return resource form DB
	 */
	Optional<User> findByEmail(String email);
}
