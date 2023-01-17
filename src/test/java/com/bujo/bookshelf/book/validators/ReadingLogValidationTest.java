package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("Test ReadingLogValidation Class")
class ReadingLogValidationTest {
    ReadingLogValidation validation = new ReadingLogValidation();

    ReadingLogDTO readingLogDto;

    final String ERR_READING_LOG_REQUIRED = "reading log is required";
    final String ERR_BOOK_REQUIRED = "book is required";
    final String ERR_START_DATE_REQUIRED = "start date is required";
    final String ERR_FUTURE_START_DATE = "start date must be in the past";
    final String ERR_START_DATE_AFTER_FINISH_DATE = "start date must be before finish date";
    final String ERR_FUTURE_FINISH_DATE = "finish date must be in the past";

    private void setReadingLogDto(Long bookId, LocalDate start, LocalDate finish) {
        readingLogDto = new ReadingLogDTO(bookId, start, finish);
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
     */
    @Test
    @DisplayName("Should validate valid")
    void testShouldValidateReadingLog() {
        setReadingLogDto(1L, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));

        Result<ReadingLogDTO> result = validation.validate(readingLogDto);

        assertTrue(result.isSuccess());
        assertEquals(ActionStatus.SUCCESS, result.getStatus());
        assertNull(result.getPayload());
    }

    private void validateErrorResult(Result<ReadingLogDTO> expected, Result<ReadingLogDTO> result) {
        assertFalse(result.isSuccess());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getMessages().size(), result.getMessages().size());
        assertArrayEquals(expected.getMessages().toArray(), result.getMessages().toArray());
        assertNull(result.getPayload());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
     */
    @Test
    @DisplayName("Should not validate null ReadingLogDTO")
    void testShouldNotValidateNullReadingLogDto() {
        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.NOT_FOUND, ERR_READING_LOG_REQUIRED);

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
     */
    @Test
    @DisplayName("Should not validate null Book ID")
    void testShouldNotValidateNullBookId() {
        setReadingLogDto(null, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_BOOK_REQUIRED);

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Nested
    @DisplayName("Test ReadingLogValidation validate start date")
    class ReadingLogValidationValidateStartDateTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
         */
        @Test
        @DisplayName("Should not validate null date")
        void testShouldNotValidateNullStart() {
            setReadingLogDto(1L, null, LocalDate.now().minusDays(1));

            Result<ReadingLogDTO> expected = new Result<>();
            expected.addMessage(ActionStatus.INVALID, ERR_START_DATE_REQUIRED);

            validateErrorResult(expected, validation.validate(readingLogDto));
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
         */
        @Test
        @DisplayName("Should not validate date in the future")
        void testShouldNotValidateFutureStart() {
            setReadingLogDto(1L, LocalDate.now().plusDays(2), null);

            Result<ReadingLogDTO> expected = new Result<>();
            expected.addMessage(ActionStatus.INVALID, ERR_FUTURE_START_DATE);

            validateErrorResult(expected, validation.validate(readingLogDto));
        }
    }

    @Nested
    @DisplayName("Test ReadingLogValidation validate finish date")
    class ReadingLogValidationValidateFinishDateTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
         */
        @Test
        @DisplayName("Should not validate date before start date")
        void testShouldNotValidateFinishBeforeStart() {
            setReadingLogDto(1L, LocalDate.now().minusDays(1), LocalDate.now().minusDays(2));

            Result<ReadingLogDTO> expected = new Result<>();
            expected.addMessage(ActionStatus.INVALID, ERR_START_DATE_AFTER_FINISH_DATE);

            validateErrorResult(expected, validation.validate(readingLogDto));
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
         */
        @Test
        @DisplayName("Should not validate date in the future")
        void testShouldNotValidateFutureFinish() {
            setReadingLogDto(1L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(2));

            Result<ReadingLogDTO> expected = new Result<>();
            expected.addMessage(ActionStatus.INVALID, ERR_FUTURE_FINISH_DATE);

            validateErrorResult(expected, validation.validate(readingLogDto));
        }
    }
}
