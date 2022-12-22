package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.book.repositories.ReadingLogRepository;
import com.bujo.bookshelf.book.validators.ReadingLogValidation;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReadingLogServiceImpl implements ReadingLogService {
    private final BookService bookService;
    private final ReadingLogRepository repository;
    private final ReadingLogValidation validation;

    public ReadingLogServiceImpl(BookService bookService, ReadingLogRepository repository, ReadingLogValidation validation) {
        this.bookService = bookService;
        this.repository = repository;
        this.validation = validation;
    }

    @Override
    public Result<ReadingLogDTO> create(ReadingLogDTO readingLogDto, Long appUserId) {
        Result<ReadingLogDTO> result = validation.validate(readingLogDto);

        if (!result.isSuccess()) {
            return result;
        }

        ReadingLog newReadingLog = new ReadingLog();
        Book book = bookService.findById(readingLogDto.bookId()).orElse(null);

        if (!isPresent(book)) {
            result.addMessage(ActionStatus.NOT_FOUND, "book not found");
            return result;
        }

        if (!book.getUser().getAppUserId().equals(appUserId)) {
            result.addMessage(ActionStatus.INVALID, "invalid request");
            return result;
        }

        newReadingLog.setBook(book);
        newReadingLog.setStart(readingLogDto.start());
        newReadingLog.setFinish(readingLogDto.finish());

        newReadingLog = repository.save(newReadingLog);
        result.setPayload(new ReadingLogDTO(
                newReadingLog.getBook().getBookId(),
                newReadingLog.getStart(),
                newReadingLog.getFinish()));

        return result;
    }

    private boolean isPresent(Object object) {
        return object != null;
    }
}
