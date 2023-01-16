package com.bujo.bookshelf.appUser;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.appUser.models.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserValidatorTest {
    @Autowired
    AppUserValidator validator;
    Errors errors;
    AppUser input;

    final String USERNAME_FIELD = "username";
    final String PASSWORD_FIELD = "password";

    @BeforeEach
    void setUp() {
        input = new AppUser("username", "P@ssw0rd!", AppUserRole.USER);
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Test
    void testShouldValidate() {
        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testEmptyUsernameShouldNotValidate() {
        input.setUsername("\t");

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(USERNAME_FIELD));
    }

    @Test
    void testNullNameShouldNotValidate() {
        input.setUsername(null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(USERNAME_FIELD));
    }

    @Test
    void testInvalidUsernameShouldNotValidate() {
        input.setUsername("hi");

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(USERNAME_FIELD));
    }

    @Test
    void testEmptyPasswordShouldNotValidate() {
        input.setPassword("   ");

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(PASSWORD_FIELD));
    }

    @Test
    void testNullPasswordShouldNotValidate() {
        input.setPassword(null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(PASSWORD_FIELD));
    }

    @Test
    void testInvalidPasswordShouldNotValidate() {
        input.setPassword("bad password");

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(PASSWORD_FIELD));
    }
}