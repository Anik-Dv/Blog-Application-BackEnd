package com.anikdv.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.Category;

/**
 * This is a Category Repository
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
