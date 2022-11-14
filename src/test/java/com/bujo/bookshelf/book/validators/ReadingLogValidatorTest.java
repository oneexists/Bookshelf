package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReadingLogValidatorTest {
    @Autowired
    ReadingLogValidator validator;
    Errors errors;
    ReadingLog input;

    @BeforeEach
    void setUp() {
        input = new ReadingLog();
        errors = new BeanPropertyBindingResult(input, "input");
    }

    @Test
    void testShouldValidate() {
        input.setBook(new Book());
        input.setStart(LocalDate.now().minusDays(2));
        input.setFinish(LocalDate.now());

        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testNullStartShouldNotValidate() {
        input.setBook(new Book());

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("start"));
    }

    @Test
    void testFutureStartShouldNotValidate() {
        input.setBook(new Book());
        input.setStart(LocalDate.now().plusDays(2));

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("start"));
    }

    @Test
    void testFutureFinishShouldNotValidate() {
        input.setBook(new Book());
        input.setStart(LocalDate.now().minusDays(2));
        input.setFinish(LocalDate.now().plusDays(4));

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("finish"));
    }
}