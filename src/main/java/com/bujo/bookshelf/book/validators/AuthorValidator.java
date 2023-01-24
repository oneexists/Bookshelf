package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.Author;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The AuthorValidator class is a validator class for the {@link Author} model. It is used to validate the
 * fields of the {@link Author} model before saving it to the database.
 *
 * @author skylar
 */
@Component("beforeSaveAuthorValidation")
public class AuthorValidator implements Validator {
    public static final String NAME_FIELD = "name";
    public static final String EMPTY_CODE = ".empty";


    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        if (isBlankString(author.getName())) {
            errors.rejectValue(NAME_FIELD, NAME_FIELD + EMPTY_CODE);
        }
    }

    private boolean isBlankString(String string ) {
        return string == null || string.isBlank();
    }

}
