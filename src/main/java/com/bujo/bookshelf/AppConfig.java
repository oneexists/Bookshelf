package com.bujo.bookshelf;

import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import org.springframework.beans.factory.annotation.Value;
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
	private final String[] allowedOrigins;

	public AppConfig(@Value("${allowed.origins}") String allowedOrigins) {
		if (allowedOrigins == null || allowedOrigins.isBlank()) {
			this.allowedOrigins = new String[0];
		} else {
			this.allowedOrigins = allowedOrigins.split("\\s*,\\s*");
		}
	}

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
						.allowedOrigins(allowedOrigins)
						.allowedMethods("*");
			}
		};
	}

	@Component
	public class SpringDataRestConfig implements RepositoryRestConfigurer {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
			cors.addMapping("/api/**")
					.allowedOrigins(allowedOrigins)
					.allowedMethods("*")
					.allowCredentials(false)
					.maxAge(3600);
			config.exposeIdsFor(Book.class);
			config.exposeIdsFor(Author.class);
			config.exposeIdsFor(ReadingLog.class);
		}
	}
}
