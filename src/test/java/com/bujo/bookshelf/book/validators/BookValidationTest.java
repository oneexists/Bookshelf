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

    final Long BOOK_ID, APP_USER_ID = BOOK_ID = 1L;
    final String THE_REGULATORS = "The Regulators";
    final String STEPHEN_KING = "Stephen King";
    final String ENGLISH = "English";
    final int PAGE_COUNT = 512;
    final String ERR_BOOK_REQUIRED = "book is required";
    final String ERR_TITLE_REQUIRED = "title is required";
    final String ERR_AT_LEAST_ONE_PAGE_REQUIRED = "book must have at least one page";
    final String ERR_AUTHOR_REQUIRED = "author is required";

    /**
     * Test method for {@link com.bujo.bookshelf.book.validators.BookValidation#validate(BookDTO)}.
     */
    @Test
    void testShouldValidateBook() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                PAGE_COUNT);

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
        expected.addMessage(ActionStatus.NOT_FOUND, ERR_BOOK_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNullTitle() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                null,
                STEPHEN_KING,
                ENGLISH,
                PAGE_COUNT);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_TITLE_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateEmptyTitle() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                "\t",
                STEPHEN_KING,
                ENGLISH,
                PAGE_COUNT);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_TITLE_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateZeroPages() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                0);

        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_AT_LEAST_ONE_PAGE_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNegativePages() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                -300);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_AT_LEAST_ONE_PAGE_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNotValidateNullAuthor() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                THE_REGULATORS,
                null,
                ENGLISH,
                PAGE_COUNT);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_AUTHOR_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }

    @Test
    void testShouldNOtValidateEmptyAuthor() {
        theRegulatorsDto = new BookDTO(
                BOOK_ID,
                APP_USER_ID,
                THE_REGULATORS,
                " ",
                ENGLISH,
                PAGE_COUNT);
        Result<BookDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_AUTHOR_REQUIRED);

        validateErrorResult(expected, validation.validate(theRegulatorsDto));
    }
}
