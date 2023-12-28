package com.anikdv.blog.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anikdv.blog.app.entities.Role;
import com.anikdv.blog.app.repositories.RoleRepository;
import com.anikdv.blog.app.utils.AppConstants;

/**
 * This is Starter Main Class
 */
@SpringBootApplication
public class SpringBootBlogApplicationBackEndApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApplicationBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// if when application is if role database table is not exist then create it.
		// FOR ADMIN ROLE
		Role adminRole = new Role();
		adminRole.setId(AppConstants.ADMIN_USER);
		adminRole.setName("ADMIN_USER");

		// FOR NORMAL ROLE
		Role normalRole = new Role();
		normalRole.setId(AppConstants.NORMAL_USER);
		normalRole.setName("NORMAL_USER");
		List<Role> roles = List.of(adminRole, normalRole);
		this.roleRepository.saveAll(roles);
	}

}
