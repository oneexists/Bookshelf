package com.bujo.bookshelf.appUser;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.appUser.models.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Test AppUserValidator Class")
class AppUserValidatorTest {
    @Autowired
    AppUserValidator validator;
    Errors errors;
    AppUser input;

    final String USERNAME_FIELD = "username";
    final String PASSWORD_FIELD = "password";
    final String EMPTY_USERNAME_CODE = "username.empty";
    final String EMPTY_PASSWORD_CODE = "password.empty";
    final String INVALID_USERNAME_CODE = "username.invalid";
    final String INVALID_PASSWORD_CODE = "password.invalid";

    @BeforeEach
    void setUp() {
        input = new AppUser("username", "P@ssw0rd!", AppUserRole.USER);
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Nested
    @DisplayName("Test AppUserValidator supports AppUser class")
    class AppUserValidatorSupportsTest {
        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#supports(Class)}.
         */
        @Test
        @DisplayName("Should support valid class")
        void testShouldSupportAppUserClass() {
            assertTrue(validator.supports(AppUser.class));
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#supports(Class)}.
         */
        @Test
        @DisplayName("Should not support invalid class")
        void testShouldNotSupportInvalidClass() {
            assertFalse(validator.supports(Long.class));
        }
    }

    @Nested
    @DisplayName("Test AppUserValidator validates AppUser")
    class AppUserValidatorValidateTest {
        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should validate valid input")
        void testShouldValidate() {
            validator.validate(input, errors);

            assertFalse(errors.hasErrors());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate empty username")
        void testEmptyUsernameShouldNotValidate() {
            input.setUsername("\t");

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(USERNAME_FIELD));
            assertEquals(EMPTY_USERNAME_CODE, Objects.requireNonNull(errors.getFieldError(USERNAME_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate null username")
        void testNullUsernameShouldNotValidate() {
            input.setUsername(null);

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(USERNAME_FIELD));
            assertEquals(EMPTY_USERNAME_CODE, Objects.requireNonNull(errors.getFieldError(USERNAME_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate invalid username length")
        void testInvalidUsernameShouldNotValidate() {
            input.setUsername("hi");

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(USERNAME_FIELD));
            assertEquals(INVALID_USERNAME_CODE, Objects.requireNonNull(errors.getFieldError(USERNAME_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate empty password")
        void testEmptyPasswordShouldNotValidate() {
            input.setPassword("   ");

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(PASSWORD_FIELD));
            assertEquals(EMPTY_PASSWORD_CODE, Objects.requireNonNull(errors.getFieldError(PASSWORD_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate null password")
        void testNullPasswordShouldNotValidate() {
            input.setPassword(null);

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(PASSWORD_FIELD));
            assertEquals(EMPTY_PASSWORD_CODE, Objects.requireNonNull(errors.getFieldError(PASSWORD_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.appUser.AppUserValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate invalid password")
        void testInvalidPasswordShouldNotValidate() {
            input.setPassword("bad password");

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(PASSWORD_FIELD));
            assertEquals(INVALID_PASSWORD_CODE, Objects.requireNonNull(errors.getFieldError(PASSWORD_FIELD)).getCode());
        }
    }
}