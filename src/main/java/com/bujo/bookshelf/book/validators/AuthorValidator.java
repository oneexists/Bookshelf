package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Author;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeSaveAuthorValidation")
public class AuthorValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        if (isBlankString(author.getName())) {
            errors.rejectValue("name", "name.empty");
        }
    }

    private boolean isBlankString(String string ) {
        return string == null || string.isBlank();
    }

}
