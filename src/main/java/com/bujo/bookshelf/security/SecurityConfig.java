package com.bujo.bookshelf.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {
	private final JwtConverter converter;
	
	public SecurityConfig(JwtConverter converter) {
		this.converter = converter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
		http.csrf().disable();
		http.cors();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers(GET).permitAll()
			.antMatchers(POST, "/create_account").permitAll()
			.antMatchers(POST, "/authenticate").permitAll()
			.antMatchers(POST, "/api/appUsers").denyAll()
			.antMatchers(DELETE, "/api/appUsers").denyAll()
			.anyRequest().authenticated();
		http.addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter));
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
