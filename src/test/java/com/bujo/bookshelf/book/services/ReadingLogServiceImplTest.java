package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.book.repositories.ReadingLogRepository;
import com.bujo.bookshelf.book.validators.ReadingLogValidation;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class ReadingLogServiceImplTest {
    ReadingLogService service;
    @MockBean
    BookService bookService;
    @MockBean
    ReadingLogRepository repository;
    @Autowired
    ReadingLogValidation validation;

    AppUser appUser = new AppUser();
    Author stephenKing = new Author();
    Book heartsInAtlantis = new Book();
    ReadingLog heartsInAtlantisLog = new ReadingLog();

    ReadingLogDTO readingLogDto;

    @BeforeEach
    void setUp() {
        service = new ReadingLogServiceImpl(bookService, repository, validation);

        appUser.setAppUserId(1L);
        stephenKing = initializeAuthor(4L, "Stephen King");
        heartsInAtlantis = initializeBook(1L, stephenKing, "Hearts in Atlantis", 640);
        heartsInAtlantisLog = initializeReadingLog(heartsInAtlantis, LocalDate.now().minusDays(3), LocalDate.now());
    }

    private Author initializeAuthor(Long id, String name) {
        Author newAuthor = new Author();
        newAuthor.setAuthorId(id);
        newAuthor.setName(name);
        return newAuthor;
    }

    private Book initializeBook(Long id, Author author, String title, int pages) {
        Book newBook = new Book();
        newBook.setUser(appUser);
        newBook.setBookId(id);
        newBook.setAuthor(author);
        newBook.setTitle(title);
        newBook.setLanguage("English");
        newBook.setPages(pages);
        return newBook;
    }

    private ReadingLog initializeReadingLog(Book book, LocalDate start, LocalDate finish) {
        ReadingLog newReadingLog = new ReadingLog();
        newReadingLog.setBook(book);
        newReadingLog.setStart(start);
        newReadingLog.setFinish(finish);
        return newReadingLog;
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.ReadingLogServiceImpl#create(ReadingLogDTO, Long)}.
     */
    @Test
    void testShouldCreateReadingLog() {
        given(bookService.findById(heartsInAtlantis.getBookId())).willReturn(Optional.of(heartsInAtlantis));
        given(repository.save(any(ReadingLog.class))).willReturn(heartsInAtlantisLog);
        ArgumentCaptor<ReadingLog> newReadingLogArgCaptor = ArgumentCaptor.forClass(ReadingLog.class);

        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        service.create(readingLogDto, appUser.getAppUserId());
        verify(repository).save(newReadingLogArgCaptor.capture());

        assertThat(newReadingLogArgCaptor.getValue().getBook().getBookId()).isEqualTo(readingLogDto.bookId());
        assertThat(newReadingLogArgCaptor.getValue().getStart()).isEqualTo(readingLogDto.start());
        assertThat(newReadingLogArgCaptor.getValue().getFinish()).isEqualTo(readingLogDto.finish());
    }

    @Test
    void testShouldNotCreateInvalidReadingLog() {
        service.create(readingLogDto, appUser.getAppUserId());
        verify(repository, never()).save(any());
    }

    private void validateErrorResult(Result<ReadingLogDTO> expected, Result<ReadingLogDTO> result) {
        assertFalse(result.isSuccess());
        assertEquals(expected.getStatus(), result.getStatus());
        assertEquals(expected.getMessages().size(), result.getMessages().size());
        assertArrayEquals(expected.getMessages().toArray(), result.getMessages().toArray());
        assertNull(result.getPayload());
    }

    @Test
    void testShouldNotCreateReadingLogMissingBook() {
        given(bookService.findById(any())).willReturn(Optional.empty());
        readingLogDto = new ReadingLogDTO(
                1_000L,
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.NOT_FOUND, "book not found");

        validateErrorResult(expected, service.create(readingLogDto, appUser.getAppUserId()));
    }

    @Test
    void testShouldNotCreateReadingLogMissingUser() {
        given(bookService.findById(heartsInAtlantis.getBookId())).willReturn(Optional.of(heartsInAtlantis));
        readingLogDto = new ReadingLogDTO(
                1L,
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(2));

        Result<ReadingLogDTO> expected = new Result<>();
        expected.addMessage(ActionStatus.INVALID, "invalid request");

        validateErrorResult(expected, service.create(readingLogDto, 1_000L));
    }
}
