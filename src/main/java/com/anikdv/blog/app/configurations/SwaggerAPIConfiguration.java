package com.anikdv.blog.app.configurations;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * OpenAPIConfiguration class
 *
 * @author anikdv
 */
@Configuration
public class SwaggerAPIConfiguration {

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
	}

	/**
	 * Note: include some essential data about the API, such as name, description,
	 * and author contacts.
	 *
	 * @return OpenAPI
	 */
	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server();
		server.setUrl("http://localhost:9090");
		server.setDescription("Development");

		Contact myContact = new Contact();
		myContact.setName("Anik Karmakar");
		myContact.setEmail("royanik815@gmail.com");

		Info information = new Info().title("Blog Application API").version("1.0")
				.description("This API exposes endpoints to manage Blog Application Post And Users etc.")
				.contact(myContact);
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
				.info(information).servers(List.of(server));
	}

}
