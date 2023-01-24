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

/**
 *  AppConfig is the main configuration class for the application. It sets up cross-origin resource sharing (CORS), a
 *  password encoder, and configuration for Spring Data REST.
 *
 * @author skylar
 * @version 1.0
 * @since 1.0
 */
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

	/**
	 * Creates a password encoder.
	 *
	 * @return a BCryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	/**
	 * Returns the WebMvcConfigurer with CORS configured.
	 *
	 * @return a WebMvcConfigurer
	 */
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
