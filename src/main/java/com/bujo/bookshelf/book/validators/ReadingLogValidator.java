package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.ReadingLog;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

/**
 * The ReadingLogValidator class is a validator class for the {@link ReadingLog} model. It is used to validate the
 * fields of the {@link ReadingLog} model before saving it to the database.
 *
 * @author skylar
 */
@Component("beforeSaveReadingLogValidation")
public class ReadingLogValidator implements Validator {
    public static final String BOOK_FIELD = "book";
    public static final String START_FIELD = "start";
    public static final String FINISH_FIELD = "finish";
    public static final String EMPTY_CODE = ".empty";
    public static final String INVALID_CODE = ".invalid";

    @Override
    public boolean supports(Class<?> clazz) {
        return ReadingLog.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReadingLog readingLog = (ReadingLog) target;

        if (readingLog.getBook() == null) {
            errors.rejectValue(BOOK_FIELD, BOOK_FIELD + EMPTY_CODE);
        }

        if (readingLog.getStart() == null) {
            errors.rejectValue(START_FIELD, START_FIELD + EMPTY_CODE);
            return;
        }

        if (readingLog.getStart().isAfter(LocalDate.now())) {
            errors.rejectValue(START_FIELD, START_FIELD + INVALID_CODE);
        }

        if (readingLog.getFinish() != null
                && (readingLog.getFinish().isAfter(LocalDate.now()) || readingLog.getFinish().isBefore(readingLog.getStart()))) {
            errors.rejectValue(FINISH_FIELD, FINISH_FIELD + INVALID_CODE);
        }
    }
}
