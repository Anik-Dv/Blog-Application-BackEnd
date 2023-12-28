package com.anikdv.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anikdv.blog.app.entities.Role;

/**
 * This is Role Repository
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
