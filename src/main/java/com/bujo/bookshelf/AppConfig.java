package com.bujo.bookshelf;

import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("*");
			}
		};
	}
	
	@Component
	public class SpringDataRestConfig implements RepositoryRestConfigurer {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
			cors.addMapping("/api/**")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("*")
				.allowCredentials(false)
				.maxAge(3600);
			config.exposeIdsFor(Book.class);
			config.exposeIdsFor(Author.class);
		}
	}
}
