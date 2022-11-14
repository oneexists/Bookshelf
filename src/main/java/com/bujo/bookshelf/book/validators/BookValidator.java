package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeSaveBookValidator")
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (isBlankString(book.getTitle())) {
            errors.rejectValue("title", "title.empty");
        }

        if (book.getPages() < 1) {
            errors.rejectValue("pages", "pages.invalid");
        }

        if (book.getAuthor() == null) {
            errors.rejectValue("author", "author.empty");
        }
    }

    public boolean isBlankString(String string ) {
        return string == null || string.isBlank();
    }

}
