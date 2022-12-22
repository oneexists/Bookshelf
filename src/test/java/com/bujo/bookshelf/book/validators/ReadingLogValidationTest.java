package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReadingLogValidationTest {
    ReadingLogValidation validation = new ReadingLogValidation();

    ReadingLogDTO readingLogDto;

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.ReadingLogValidation#validate(ReadingLogDTO)}.
     */
    @Test
    void testShouldValidateReadingLog() {
        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().minusDays(2),
                LocalDate.now().minusDays(1));

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

    @Test
    void testShouldNotValidateNullReadingLogDto() {
        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.NOT_FOUND, "reading log is required");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Test
    void testShouldNotValidateNullBookId() {
        readingLogDto = new ReadingLogDTO(
                null,
                LocalDate.now().minusDays(2),
                LocalDate.now().minusDays(1));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "book is required");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Test
    void testShouldNotValidateNullStart() {
        readingLogDto = new ReadingLogDTO(
                1L,
                null,
                LocalDate.now().minusDays(1));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "start date is required");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Test
    void testShouldNotValidateFutureStart() {
        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().plusDays(2),
                null);

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "start date must be in the past");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Test
    void testShouldNotValidateFinishBeforeStart() {
        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().minusDays(1),
                LocalDate.now().minusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "start date must be before finish date");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }

    @Test
    void testShouldNotValidateFutureFinish() {
        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "finish date must be in the past");

        validateErrorResult(expected, validation.validate(readingLogDto));
    }
}
