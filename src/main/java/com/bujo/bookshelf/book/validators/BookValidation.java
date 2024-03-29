package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.springframework.stereotype.Component;

/**
 * This class provides the validation for {@link BookDTO} objects.
 * It implements validation checks for title, pages, and author name.
 * If any of the validation checks fail, it adds a message to a {@link Result} object,
 * and returns the result with the appropriate {@link ActionStatus}.
 *
 * @author skylar
 */
@Component
public class BookValidation {
    public Result<BookDTO> validate(BookDTO book) {
        Result<BookDTO> result = new Result<>();

        if (book == null) {
            result.addMessage(ActionStatus.NOT_FOUND, "book is required");
            return result;
        }

        if (isBlankString(book.title())) {
            result.addMessage(ActionStatus.INVALID, "title is required");
        }

        if (book.pages() < 1) {
            result.addMessage(ActionStatus.INVALID, "book must have at least one page");
        }

        if (isBlankString(book.author())) {
            result.addMessage(ActionStatus.INVALID, "author is required");
        }

        return result;
    }

    private boolean isBlankString(String string ) {
        return string == null || string.isBlank();
    }

}
