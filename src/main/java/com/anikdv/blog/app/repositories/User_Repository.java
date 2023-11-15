package com.anikdv.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.User;

/**
 * @author AnikDv User Repository
 */
public interface User_Repository extends JpaRepository<User, Integer> {
}
