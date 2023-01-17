package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Test ReadingLogValidator Class")
class ReadingLogValidatorTest {
    @Autowired
    ReadingLogValidator validator;
    Errors errors;
    ReadingLog input;

    final String BOOK_FIELD = "book";
    final String START_FIELD = "start";
    final String FINISH_FIELD = "finish";
    final String EMPTY_BOOK_CODE = "book.empty";
    final String INVALID_FINISH_CODE = "finish.invalid";
    final String EMPTY_START_CODE = "start.empty";
    final String INVALID_START_CODE = "start.invalid";

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

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#supports(Class)}.
     */
    @Test
    @DisplayName("Should support ReadingLog class")
    void testShouldSupportReadingLogClass() {
        assertTrue(validator.supports(ReadingLog.class));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#supports(Class)}.
     */
    @Test
    @DisplayName("Should not support invalid class")
    void testShouldNotSupportInvalidClass() {
        assertFalse(validator.supports(String.class));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should validate valid ReadingLog")
    void testShouldValidate() {
        setInput(LocalDate.now().minusDays(2), LocalDate.now());

        validator.validate(input, errors);

        assertFalse(errors.hasErrors());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should not validate null Book")
    void testShouldNotValidateNullBook() {
        input.setStart(LocalDate.now());

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(BOOK_FIELD));
        assertEquals(EMPTY_BOOK_CODE, Objects.requireNonNull(errors.getFieldError(BOOK_FIELD)).getCode());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should not validate null start date")
    void testNullStartShouldNotValidate() {
        setInput(null, null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(START_FIELD));
        assertEquals(EMPTY_START_CODE, Objects.requireNonNull(errors.getFieldError(START_FIELD)).getCode());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should not validate start date in the future")
    void testFutureStartShouldNotValidate() {
        setInput(LocalDate.now().plusDays(2), null);

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(START_FIELD));
        assertEquals(INVALID_START_CODE, Objects.requireNonNull(errors.getFieldError(START_FIELD)).getCode());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should not validate finish date in the future")
    void testFutureFinishShouldNotValidate() {
        setInput(LocalDate.now().minusDays(2), LocalDate.now().plusDays(4));

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(FINISH_FIELD));
        assertEquals(INVALID_FINISH_CODE, Objects.requireNonNull(errors.getFieldError(FINISH_FIELD)).getCode());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidator#validate(java.lang.Object, org.springframework.validation.Errors)}.
     */
    @Test
    @DisplayName("Should not validate finish date before start date")
    void testFinishBeforeStartShouldNotValidate() {
        setInput(LocalDate.now(), LocalDate.now().minusDays(2));

        validator.validate(input, errors);

        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError(FINISH_FIELD));
        assertEquals(INVALID_FINISH_CODE, Objects.requireNonNull(errors.getFieldError(FINISH_FIELD)).getCode());
    }
}