package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookValidatorTest {
    @Autowired
    BookValidator validator;
    Errors errors;
    Book input;

    @BeforeEach
    void setUp() {
        input = new Book();
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Test
    void testShouldValidate() {
        input.setTitle("title");
        input.setAuthor(new Author());
        input.setPages(55);

        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testEmptyTitleShouldNotValidate() {
        input.setTitle("\t");
        input.setAuthor(new Author());
        input.setPages(26);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("title"));
    }

    @Test
    void testNullTitleShouldNotValidate() {
        input.setAuthor(new Author());
        input.setPages(26);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("title"));
    }

    @Test
    void testZeroPagesShouldNotValidate() {
        input.setTitle("title");
        input.setAuthor(new Author());
        input.setPages(0);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("pages"));
    }

    @Test
    void testNegativePagesShouldNotValidate() {
        input.setTitle("title");
        input.setAuthor(new Author());
        input.setPages(-22);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("pages"));
    }

    @Test
    void testNullAuthorShouldNotValidate() {
        input.setTitle("title");
        input.setPages(22);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("author"));
    }
}