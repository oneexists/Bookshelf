package com.bujo.bookshelf.appUser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.appUser.models.AppUserRole;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test AppUserRepository Interface")
class AppUserRepositoryTest {
	@Autowired
	AppUserRepository repository;
	@Autowired
	JdbcTemplate jdbcTemplate;

	final String USERNAME = "username";
	final String PASSWORD = "$2a$10$bJ.Q1/9A/1i4LpO90CVnHO.DK464jvQnrXUo0QHJggWEhgLF3eElm";

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("call set_known_good_state();");
	}

	@Test
	@DisplayName("Should find existing AppUser by ID")
	void testShouldFindById() {
		AppUser expected = new AppUser(USERNAME, PASSWORD, AppUserRole.USER);

		AppUser actual = repository.findById(1L).orElse(null);

		assertNotNull(actual);
		assertEquals(expected.getUsername(), actual.getUsername());
	}

	@Test
	@DisplayName("Should find AppUser by existing username")
	void testShouldFindByUsername() {
		AppUser expected = new AppUser(USERNAME, PASSWORD, AppUserRole.USER);
		
		AppUser actual = repository.findByUsername(USERNAME).orElse(null);
		
		assertNotNull(actual);
		assertEquals(expected.getUsername(), actual.getUsername());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getUserRole(), actual.getUserRole());
		assertEquals(expected.isAccountNonExpired(), actual.isAccountNonExpired());
		assertEquals(expected.isAccountNonLocked(), actual.isAccountNonLocked());
		assertEquals(expected.isCredentialsNonExpired(), actual.isCredentialsNonExpired());
		assertEquals(expected.isEnabled(), actual.isEnabled());
	}

	@Test
	@DisplayName("Should not find AppUser by username that does not exist")
	void testShouldNotFindByUsername() {
		AppUser actual = repository.findByUsername("not a username").orElse(null);
		
		assertNull(actual);
	}

	@Test
	@DisplayName("Should not create a new AppUser with existing username")
	void testShouldNotCreateDuplicateUsername() {
		AppUser duplicate = new AppUser(USERNAME, PASSWORD, AppUserRole.USER);
		
		assertThatThrownBy(() -> repository.save(duplicate))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

}
