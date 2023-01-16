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

    final String START_FIELD = "start";
    final String FINISH_FIELD = "finish";

    @BeforeEach
    void setUp() {
        input = new ReadingLog();
        errors = new BeanPropertyBindingResult(input, "input");
    }

    private void setInput(LocalDate start, LocalDate finish) {
        input.setBook(new Book());
        input.setStart(start);
        input.setFinish(finish);
    }

    @Test
    void testShouldValidate() {
        setInput(LocalDate.now().minusDays(2), LocalDate.now());

        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void testNullStartShouldNotValidate() {
        setInput(null, null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(START_FIELD));
    }

    @Test
    void testFutureStartShouldNotValidate() {
        setInput(LocalDate.now().plusDays(2), null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(START_FIELD));
    }

    @Test
    void testFutureFinishShouldNotValidate() {
        setInput(LocalDate.now().minusDays(2), LocalDate.now().plusDays(4));

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(FINISH_FIELD));
    }
}