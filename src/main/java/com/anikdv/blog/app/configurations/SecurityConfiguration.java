package com.anikdv.blog.app.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

	private static final String[] PUBLIC_URL = { "api/auth/**", "api/posts/feed", "/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**" };

	/**
	 * @param security
	 * @throws Exception
	 * @return security
	 */
	@Bean
	public SecurityFilterChain configuration(HttpSecurity security) throws Exception {
		security.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				authorize -> authorize.requestMatchers(PUBLIC_URL).permitAll().anyRequest().authenticated());
		security.exceptionHandling(e -> e.authenticationEntryPoint(this.point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
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
}
