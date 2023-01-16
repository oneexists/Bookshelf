package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AuthorValidatorTest {
    @Autowired
    AuthorValidator validator;
    Errors errors;
    Author input;

    final String NAME_FIELD = "name";

    @BeforeEach
    void setUp() {
        input = new Author();
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Test
    void testShouldValidate() {
        input.setName("author name");

        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testEmptyNameShouldNotValidate() {
        input.setName("   ");

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(NAME_FIELD));
    }

    @Test
    void testNullNameShouldNotValidate() {
        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(NAME_FIELD));
    }
}