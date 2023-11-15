package com.anikdv.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.User;

/**
 * @author AnikDv User Repository
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
