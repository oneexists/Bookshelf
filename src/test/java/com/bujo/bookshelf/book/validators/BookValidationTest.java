package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookValidationTest {
    BookValidation validation = new BookValidation();

    BookDTO theRegulatorsDto;

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.BookValidation#validate(BookDTO)}.
     */
    @Test
    void testShouldValidateBook() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                512);

        Result<BookDTO> result = validation.validate(theRegulatorsDto);

        assertTrue(result.isSuccess());
        assertEquals(ActionStatus.SUCCESS, result.getStatus());
        assertNull(result.getPayload());
    }

    private void validateErrorResult(Result<BookDTO> expected, Result<BookDTO> result) {
        assertFalse(result.isSuccess());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getMessages().size(), result.getMessages().size());
        assertArrayEquals(expected.getMessages().toArray(), result.getMessages().toArray());
        assertNull(result.getPayload());
    }

    @Test
    void testShouldNotValidateNullBookDto() {
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.NOT_FOUND, "book is required");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNullTitle() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                null,
                "Stephen King",
                "English",
                512);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "title is required");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateEmptyTitle() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "\t",
                "Stephen King",
                "English",
                512);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "title is required");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateZeroPages() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                0);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "book must have at least one page");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNegativePages() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                -300);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "book must have at least one page");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNullAuthor() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "The Regulators",
                null,
                "English",
                512);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "author is required");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNOtValidateEmptyAuthor() {
        theRegulatorsDto = new BookDTO(
                1L,
                1L,
                "The Regulators",
                " ",
                "English",
                512);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "author is required");

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }
}
