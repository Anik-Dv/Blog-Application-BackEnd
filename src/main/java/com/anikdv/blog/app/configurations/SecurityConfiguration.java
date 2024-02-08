package com.anikdv.blog.app.configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.anikdv.blog.app.security.CustomUserDetailsService;
import com.anikdv.blog.app.security.JwtAuthenticationEntryPoint;
import com.anikdv.blog.app.security.JwtAuthenticationFilter;

/**
 * This is Security Configuration
 *
 * @author anikdv
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint point;
	@Autowired
	private JwtAuthenticationFilter filter;

	private static final String[] PUBLIC_URL = {"api/auth/**", "/api-docs", "api/posts/feed", "api/post/{postId}",
			"api/post/file/{fileName}", "/api/search/post/{keyword}", "api/post/{post_id}/comments", "api/comments",
			"api/comment/{comment_id}", "api/user/{user_id}/comments", "api/category/{categoryId}/posts",
			"api/user/{userId}/posts", "/api/category/","/api/category/{categoryId}", "api/user/", "api/user/{userId}",
			"api/user/file/{fileName}", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };

	/**
	 * @param security
	 * @throws Exception
	 * @return security
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain configuration(HttpSecurity security) throws Exception {
		security.cors(Customizer.withDefaults());
		security.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				authorize ->
				authorize.requestMatchers(PUBLIC_URL).permitAll().anyRequest().authenticated());
		security.exceptionHandling(e -> e.authenticationEntryPoint(this.point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setMaxAge(3600L);
		//corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Accept"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

//		 FilterRegistrationBean<?> filterRegistrationBean = new FilterRegistrationBean<>();
//		 filterRegistrationBean.setOrder(-110);
		return source;
	}


	/**
	 * @param builder
	 * @throws Exception
	 * @return authenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return new ProviderManager(provider);
	}

	/**
	 * @return BCryptPasswordEncoder;
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.addAllowedOriginPattern("*");
//		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addAllowedHeader("Content-Type");
//		corsConfiguration.addAllowedHeader("Accept");
//		corsConfiguration.addAllowedHeader("POST");
//		corsConfiguration.addAllowedHeader("PUT");
//		corsConfiguration.addAllowedHeader("DELETE");
//		corsConfiguration.addAllowedHeader("GET");
//		corsConfiguration.addAllowedHeader("OPTIONS");
//		corsConfiguration.setMaxAge(3600L);
//
//		//NOTE: UPDATE "/**" TO PRODUCTION FORNT-END APP URL
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(source));
//		return registrationBean;
//	}



}
