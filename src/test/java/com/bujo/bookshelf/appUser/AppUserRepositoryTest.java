package com.bujo.bookshelf.appUser;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bujo.bookshelf.appUser.models.AppUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(AppUserParameterResolver.class)
@DisplayName("Test AppUserRepository Interface")
class AppUserRepositoryTest {
	@Autowired
	AppUserRepository repository;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("call set_known_good_state();");
	}

	@Test
	@DisplayName("Should find existing AppUser by ID")
	void testShouldFindById(AppUser expected) {
		AppUser actual = repository.findById(1L).orElse(null);

		assertNotNull(actual);
		assertEquals(expected.getUsername(), actual.getUsername());
	}

	@Test
	@DisplayName("Should not create a new AppUser with existing username")
	void testShouldNotCreateDuplicateUsername(AppUser duplicate) {
		assertThatThrownBy(() -> repository.save(duplicate))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@Nested
	@ExtendWith(AppUserParameterResolver.class)
	@DisplayName("Test AppUserRepository find AppUser by username")
	class AppUserFindByUsernameTest {
		@Test
		@DisplayName("Should find by existing username")
		void testShouldFindByUsername(AppUser expected) {
			AppUser actual = repository.findByUsername(expected.getUsername()).orElse(null);

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
		@DisplayName("Should not find by username that does not exist")
		void testShouldNotFindByUsername() {
			AppUser actual = repository.findByUsername("not a username").orElse(null);

			assertNull(actual);
		}
	}
}
