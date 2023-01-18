package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.ReadingLog;
import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.book.repositories.ReadingLogRepository;
import com.bujo.bookshelf.book.validators.ReadingLogValidation;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(ReadingLogParameterResolver.class)
@DisplayName("Test ReadingLogServiceImpl Class")
class ReadingLogServiceImplTest {
    ReadingLogService service;
    @MockBean
    BookService bookService;
    @MockBean
    ReadingLogRepository repository;
    @Autowired
    ReadingLogValidation validation;

    final String ERR_BOOK_NOT_FOUND = "book not found";
    final String ERR_INVALID_REQUEST = "invalid request";

    ReadingLogDTO readingLogDto;

    @BeforeEach
    void setUp() {
        service = new ReadingLogServiceImpl(bookService, repository, validation);
    }

    private void validateErrorResult(Result<ReadingLogDTO> expected, Result<ReadingLogDTO> result) {
        assertFalse(result.isSuccess());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getMessages().size(), result.getMessages().size());
        assertArrayEquals(expected.getMessages().toArray(), result.getMessages().toArray());
        assertNull(result.getPayload());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.ReadingLogServiceImpl#create(ReadingLogDTO, Long)}.
     */
    @Test
    @DisplayName("Should create valid ReadingLog")
    void testShouldCreateReadingLog(ReadingLog readingLog) {
        given(bookService.findById(readingLog.getBook().getBookId())).willReturn(Optional.of(readingLog.getBook()));
        given(repository.save(any(ReadingLog.class))).willReturn(readingLog);
        ArgumentCaptor<ReadingLog> newReadingLogArgCaptor = ArgumentCaptor.forClass(ReadingLog.class);

        readingLogDto = new ReadingLogDTO(
                readingLog.getBook().getBookId(),
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        service.create(readingLogDto, readingLog.getBook().getUser().getAppUserId());
        verify(repository).save(newReadingLogArgCaptor.capture());

        assertThat(newReadingLogArgCaptor.getValue().getBook().getBookId()).isEqualTo(readingLogDto.bookId());
        assertThat(newReadingLogArgCaptor.getValue().getStart()).isEqualTo(readingLogDto.start());
        assertThat(newReadingLogArgCaptor.getValue().getFinish()).isEqualTo(readingLogDto.finish());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.ReadingLogServiceImpl#create(ReadingLogDTO, Long)}.
     */
    @Test
    @DisplayName("Should not create ReadingLog with null properties")
    void testShouldNotCreateInvalidReadingLog(ReadingLog readingLog) {
        service.create(readingLogDto, readingLog.getBook().getUser().getAppUserId());
        verify(repository, never()).save(any());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.ReadingLogServiceImpl#create(ReadingLogDTO, Long)}.
     */
    @Test
    @DisplayName("Should not create ReadingLog with Book that does not exist")
    void testShouldNotCreateReadingLogMissingBook(ReadingLog readingLog) {
        given(bookService.findById(any())).willReturn(Optional.empty());
        readingLogDto = new ReadingLogDTO(
                1_000L,
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.NOT_FOUND, ERR_BOOK_NOT_FOUND);

        validateErrorResult(expected, service.create(readingLogDto, readingLog.getBook().getUser().getAppUserId()));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.ReadingLogServiceImpl#create(ReadingLogDTO, Long)}.
     */
    @Test
    @DisplayName("Should not create ReadingLog for AppUser that does not exist")
    void testShouldNotCreateReadingLogMissingUser(ReadingLog readingLog) {
        given(bookService.findById(readingLog.getBook().getBookId())).willReturn(Optional.of(readingLog.getBook()));
        readingLogDto = new ReadingLogDTO(
                readingLog.getBook().getBookId(),
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, ERR_INVALID_REQUEST);

        validateErrorResult(expected, service.create(readingLogDto, 1_000L));
    }
}
