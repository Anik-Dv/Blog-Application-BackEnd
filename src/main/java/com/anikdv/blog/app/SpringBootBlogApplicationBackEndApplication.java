package com.anikdv.blog.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 */
@SpringBootApplication
public class SpringBootBlogApplicationBackEndApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder encoder;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApplicationBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(encoder.encode("himaloy1234"));
	}

}
