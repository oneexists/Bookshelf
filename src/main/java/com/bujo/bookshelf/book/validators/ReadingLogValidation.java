package com.bujo.bookshelf.book.validators;

import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReadingLogValidation {
    public Result<ReadingLogDTO> validate(ReadingLogDTO readingLog) {
        Result<ReadingLogDTO> result = new Result<>();

        if (readingLog == null) {
            result.addMessage(ActionStatus.NOT_FOUND, "reading log is required");
            return result;
        }

        if (readingLog.bookId() == null) {
            result.addMessage(ActionStatus.INVALID, "book is required");
        }

        if (readingLog.start() == null) {
            result.addMessage(ActionStatus.INVALID, "start date is required");
            return result;
        }

        if (readingLog.start().isAfter(LocalDate.now())) {
            result.addMessage(ActionStatus.INVALID, "start date must be in the past");
        }

        if (readingLog.finish() != null) {
            if (readingLog.finish().isBefore(readingLog.start())) {
                result.addMessage(ActionStatus.INVALID, "start date must be before finish date");
            }
            if (readingLog.finish().isAfter(LocalDate.now())) {
                result.addMessage(ActionStatus.INVALID, "finish date must be in the past");
            }
        }

        return result;
    }
}
