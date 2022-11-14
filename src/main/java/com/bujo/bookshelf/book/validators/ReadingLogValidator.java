package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.ReadingLog;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component("beforeSaveReadingLogValidation")
public class ReadingLogValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ReadingLog.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReadingLog readingLog = (ReadingLog) target;

        if (readingLog.getBook() == null) {
            errors.rejectValue("book", "book.empty");
        }

        if (readingLog.getStart() == null) {
            errors.rejectValue("start", "start.empty");
            return;
        }

        if (readingLog.getStart().isAfter(LocalDate.now())) {
            errors.rejectValue("start", "start.invalid");
        }

        if (readingLog.getFinish() != null && readingLog.getFinish().isAfter(LocalDate.now())) {
            errors.rejectValue("finish", "finish.invalid");
        }
    }

}
