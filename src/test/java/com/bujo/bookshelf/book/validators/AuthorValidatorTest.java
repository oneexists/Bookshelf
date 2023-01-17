package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Author;
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
@DisplayName("Test AuthorValidator Class")
class AuthorValidatorTest {
    @Autowired
    AuthorValidator validator;
    Errors errors;
    Author input;

    @BeforeEach
    void setUp() {
        input = new Author();
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Nested
    @DisplayName("Test AuthorValidator supports")
    class AuthorValidatorSupportsTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.AuthorValidator#supports(Class)}.
         */
        @Test
        @DisplayName("Should support Author class")
        void testShouldSupportAuthorClass() {
            assertTrue(validator.supports(Author.class));
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.AuthorValidator#supports(Class)}.
         */
        @Test
        @DisplayName("Should not support invalid class")
        void testShouldNotSupportInvalidClass() {
            assertFalse(validator.supports(Object.class));
        }
    }

    @Nested
    @DisplayName("Test AuthorValidator validate")
    class AuthorValidatorValidateTest {
        final String NAME_FIELD = "name";
        final String EMPTY_NAME_CODE = "name.empty";

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.AuthorValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should validate valid")
        void testShouldValidate() {
            input.setName("author name");

            validator.validate(input, errors);

            assertFalse(errors.hasErrors());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.AuthorValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate empty name")
        void testEmptyNameShouldNotValidate() {
            input.setName("   ");

            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(NAME_FIELD));
            assertEquals(EMPTY_NAME_CODE, Objects.requireNonNull(errors.getFieldError(NAME_FIELD)).getCode());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.AuthorValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
         */
        @Test
        @DisplayName("Should not validate null name")
        void testNullNameShouldNotValidate() {
            validator.validate(input, errors);

            assertTrue(errors.hasErrors());
            assertNotNull(errors.getFieldError(NAME_FIELD));
        }
    }
}