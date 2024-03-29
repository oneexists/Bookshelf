package com.bujo.bookshelf.appUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.appUser.models.AppUserDTO;
import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.response.Result;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Test AppUserServiceImpl Class")
class AppUserServiceImplTest {
	AppUserService service;
	@MockBean
	AppUserRepository repository;
	@MockBean
	PasswordEncoder passwordEncoder;
	@Autowired
	AppUserValidation validation;

	final String USERNAME_REQUIRED = "username is required";
	final String PASSWORD_REQUIRED = "password is required";
	final String USERNAME_EXISTS = "provided username already exists";

	@BeforeEach
	void setUp() {
		service = new AppUserServiceImpl(repository, passwordEncoder, validation);
	}

	@Nested
	@ExtendWith(AppUserParameterResolver.class)
	@DisplayName("Test AppUserService load AppUser by username")
	class AppUserLoadByUsernameTest {
		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#loadUserByUsername(java.lang.String)}.
		 */
		@Test
		@DisplayName("Should load existing username")
		void testLoadUserByUsername(AppUser expected) {
			expected.setAppUserId(2L);
			given(repository.findByUsername(expected.getUsername())).willReturn(Optional.of(expected));
			ArgumentCaptor<String> loadArgCaptor = ArgumentCaptor.forClass(String.class);

			service.loadUserByUsername(expected.getUsername());
			verify(repository).findByUsername(loadArgCaptor.capture());

			assertThat(loadArgCaptor.getValue()).isEqualTo(expected.getUsername());
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#loadUserByUsername(java.lang.String)}.
		 */
		@Test
		@DisplayName("Should not load username that does not exist")
		void testShouldNotLoadUserByUsername() {
			String input = "missing username";
			given(repository.findByUsername(input)).willReturn(Optional.empty());

			assertThatThrownBy(() -> service.loadUserByUsername(input)).isInstanceOf(UsernameNotFoundException.class);
		}
	}

	@Nested
	@ExtendWith(AppUserParameterResolver.class)
	@DisplayName("Test AppUserService create AppUser")
	class AppUserCreateTest {
		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should create when valid")
		void testShouldCreate(AppUser expected) {
			AppUserDTO appUserDto = new AppUserDTO(expected.getUsername(), expected.getPassword());
			expected.setAppUserId(2L);
			given(passwordEncoder.encode(any(String.class))).willReturn(expected.getPassword());
			given(repository.save(any(AppUser.class))).willReturn(expected);
			ArgumentCaptor<AppUser> createArgCaptor = ArgumentCaptor.forClass(AppUser.class);

			service.create(appUserDto);
			verify(repository).save(createArgCaptor.capture());

			assertThat(createArgCaptor.getValue().getUsername()).isEqualTo(expected.getUsername());
			assertThat(createArgCaptor.getValue().getPassword()).isEqualTo(expected.getPassword());
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should not create null username")
		void testShouldNotCreateNullUsername(AppUser expected) {
			AppUserDTO appUserDto = new AppUserDTO(null, expected.getPassword());

			Result<AppUserDetails> result = service.create(appUserDto);

			assertNotNull(result);
			assertFalse(result.isSuccess());
			assertNull(result.getPayload());
			assertEquals(1, result.getMessages().size());
			assertTrue(result.getMessages().get(0).contains(USERNAME_REQUIRED));
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should not create with empty username")
		void testShouldNotCreateEmptyUsername(AppUser expected) {
			AppUserDTO appUserDto = new AppUserDTO("\t", expected.getPassword());

			Result<AppUserDetails> result = service.create(appUserDto);

			assertNotNull(result);
			assertFalse(result.isSuccess());
			assertNull(result.getPayload());
			assertEquals(1, result.getMessages().size());
			assertTrue(result.getMessages().get(0).contains(USERNAME_REQUIRED));
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should not create with null password")
		void testShouldNotCreateNullPassword(AppUser expected) {
			AppUserDTO appUserDto = new AppUserDTO(expected.getUsername(), null);

			Result<AppUserDetails> result = service.create(appUserDto);

			assertNotNull(result);
			assertFalse(result.isSuccess());
			assertNull(result.getPayload());
			assertEquals(1, result.getMessages().size());
			assertTrue(result.getMessages().get(0).contains(PASSWORD_REQUIRED));
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should not create with empty password")
		void testShouldNotCreateEmptyPassword(AppUser expected) {
			AppUserDTO appUserDto = new AppUserDTO(expected.getUsername(), "   ");

			Result<AppUserDetails> result = service.create(appUserDto);

			assertNotNull(result);
			assertFalse(result.isSuccess());
			assertNull(result.getPayload());
			assertEquals(1, result.getMessages().size());
			assertTrue(result.getMessages().get(0).contains(PASSWORD_REQUIRED));
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#create(com.bujo.bookshelf.appUser.models.AppUserDTO)}.
		 */
		@Test
		@DisplayName("Should not create AppUser with existing username")
		void testShouldNotCreateDuplicateUsername(AppUser expected) {
			given(repository.save(any(AppUser.class))).willThrow(DataIntegrityViolationException.class);
			AppUserDTO appUserDto = new AppUserDTO(expected.getUsername(), expected.getPassword());

			Result<AppUserDetails> result = service.create(appUserDto);

			assertNotNull(result);
			assertFalse(result.isSuccess());
			assertNull(result.getPayload());
			assertEquals(1, result.getMessages().size());
			assertTrue(result.getMessages().get(0).contains(USERNAME_EXISTS));
		}
	}

	@Nested
	@ExtendWith(AppUserParameterResolver.class)
	@DisplayName("Test AppUserService find AppUser by ID")
	class AppUserFindByIdTest {
		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#findById(Long)}.
		 */
		@Test
		@DisplayName("Should find existing AppUser by ID")
		void testShouldFindExistingAppUserById(AppUser appUser) {
			appUser.setAppUserId(1L);
			given(repository.findById(1L)).willReturn(Optional.of(appUser));

			assertNotNull(service.findById(1L).orElse(null));
		}

		/**
		 * Test method for {@link com.bujo.bookshelf.appUser.AppUserServiceImpl#findById(Long)}.
		 */
		@Test
		@DisplayName("Should not find missing AppUser by ID")
		void testShouldNotFindMissingAppUserById() {
			assertEquals(Optional.empty(), service.findById(1L));
		}
	}
}
