package com.bujo.bookshelf.book;

import com.bujo.bookshelf.appUser.AppUserRepository;
import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.repositories.AuthorRepository;
import com.bujo.bookshelf.book.repositories.BookRepository;
import com.bujo.bookshelf.book.services.BookService;
import com.bujo.bookshelf.book.services.BookServiceImpl;
import com.bujo.bookshelf.book.services.BookValidation;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookServiceImplTest {
    BookService service;
    @MockBean
    BookRepository bookRepository;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    AppUserRepository appUserRepository;
    @Autowired
    BookValidation validation;

    AppUser appUser = new AppUser();
    Author kurtVonnegut = new Author();
    Author stephenKing = new Author();
    Author richardBachman = new Author();
    Book hocusPocus = new Book();
    Book heartsInAtlantis = new Book();
    Book theRegulators = new Book();

    @BeforeEach
    void setUp() {
        service = new BookServiceImpl(bookRepository, authorRepository, appUserRepository, validation);

        appUser.setAppUserId(1L);

        stephenKing = initializeAuthor(4L, "Stephen King");
        kurtVonnegut = initializeAuthor(5L, "Kurt Vonnegut");
        richardBachman = initializeAuthor(6L, "Richard Bachman");

        theRegulators = initializeBook(3L, stephenKing, "The Regulators", 512);
        heartsInAtlantis = initializeBook(4L, stephenKing, "Hearts in Atlantis", 640);
        hocusPocus = initializeBook(5L, kurtVonnegut, "Hocus Pocus", 322);

        stephenKing.setBooks(Set.of(theRegulators, heartsInAtlantis));
        kurtVonnegut.setBooks(Set.of(hocusPocus));
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

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    void testCreateBookExistingAuthor() {
        stephenKing.setBooks(Set.of(heartsInAtlantis));
        given(authorRepository.findByName(stephenKing.getName())).willReturn(stephenKing);
        given(bookRepository.save(any(Book.class))).willReturn(theRegulators);
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                theRegulators.getAuthor().getName(),
                theRegulators.getLanguage(),
                theRegulators.getPages());

        service.create(bookDto);
        verify(bookRepository).save(newBookArgCaptor.capture());

        assertNull(newBookArgCaptor.getValue().getBookId());
        assertThat(newBookArgCaptor.getValue().getUser().getAppUserId()).isEqualTo(bookDto.appUserId());
        assertThat(newBookArgCaptor.getValue().getTitle()).isEqualTo(bookDto.title());
        assertThat(newBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
        assertThat(newBookArgCaptor.getValue().getLanguage()).isEqualTo(bookDto.language());
        assertThat(newBookArgCaptor.getValue().getPages()).isEqualTo(bookDto.pages());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    void testCreateBookNewAuthor() {
        theRegulators.setAuthor(richardBachman);
        given(authorRepository.findByName(richardBachman.getName())).willReturn(richardBachman);
        given(bookRepository.save(any(Book.class))).willReturn(theRegulators);
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                theRegulators.getAuthor().getName(),
                theRegulators.getLanguage(),
                theRegulators.getPages());

        service.create(bookDto);
        verify(bookRepository).save(newBookArgCaptor.capture());

        assertNull(newBookArgCaptor.getValue().getBookId());
        assertThat(newBookArgCaptor.getValue().getUser().getAppUserId()).isEqualTo(bookDto.appUserId());
        assertThat(newBookArgCaptor.getValue().getTitle()).isEqualTo(bookDto.title());
        assertThat(newBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
        assertThat(newBookArgCaptor.getValue().getLanguage()).isEqualTo(bookDto.language());
        assertThat(newBookArgCaptor.getValue().getPages()).isEqualTo(bookDto.pages());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateAuthorOneBook() {
        given(authorRepository.findById(kurtVonnegut.getAuthorId())).willReturn(Optional.of(kurtVonnegut));
        given(authorRepository.findByName(kurtVonnegut.getName())).willReturn(kurtVonnegut);
        given(bookRepository.findById(hocusPocus.getBookId())).willReturn(Optional.of(hocusPocus));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                5L,
                1L,
                "Hocus Pocus",
                "Kurt Vonnegut Jr.",
                "English",
                340);

        service.update(bookDto);
        verify(authorRepository).save(saveAuthorArgCaptor.capture());
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveAuthorArgCaptor.getValue().getAuthorId()).isEqualTo(kurtVonnegut.getAuthorId());
        assertThat(saveAuthorArgCaptor.getValue().getName()).isEqualTo(bookDto.author());
        assertThat(saveBookArgCaptor.getValue().getPages()).isEqualTo(bookDto.pages());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateAuthorTwoBooks() {
        given(authorRepository.findById(stephenKing.getAuthorId())).willReturn(Optional.of(stephenKing));
        given(authorRepository.findByName(stephenKing.getName())).willReturn(stephenKing);
        given(authorRepository.save(any(Author.class))).willReturn(richardBachman);
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Richard Bachman",
                "English",
                512);

        service.update(bookDto);
        verify(authorRepository).save(saveAuthorArgCaptor.capture());
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveAuthorArgCaptor.getValue().getAuthorId()).isNotEqualTo(stephenKing.getAuthorId());
        assertThat(saveAuthorArgCaptor.getValue().getName()).isEqualTo(bookDto.author());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateBookToExistingAuthor() {
        theRegulators.setAuthor(richardBachman);
        richardBachman.setBooks(Set.of(theRegulators));
        stephenKing.setBooks(Set.of(heartsInAtlantis));

        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        given(authorRepository.findById(richardBachman.getAuthorId())).willReturn(Optional.of(richardBachman));
        given(authorRepository.findByName(stephenKing.getName())).willReturn(stephenKing);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                512);

        service.update(bookDto);
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveBookArgCaptor.getValue().getAuthor().getAuthorId()).isEqualTo(stephenKing.getAuthorId());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(stephenKing.getName());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testDeleteAuthorWithoutBooks() {
        theRegulators.setAuthor(richardBachman);
        richardBachman.setBooks(Set.of(theRegulators));
        stephenKing.setBooks(Set.of(heartsInAtlantis));

        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        given(authorRepository.findById(richardBachman.getAuthorId())).willReturn(Optional.of(richardBachman));
        given(authorRepository.findByName(stephenKing.getName())).willReturn(stephenKing);
        ArgumentCaptor<Long> deleteAuthorIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                512);

        service.update(bookDto);
        verify(authorRepository).deleteById(deleteAuthorIdArgCaptor.capture());

        assertEquals(richardBachman.getAuthorId(), deleteAuthorIdArgCaptor.getValue());
    }

    @Test
    void testShouldNotUpdateMissingBook() {
        given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

        BookDTO bookDto = new BookDTO(
                1_000L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book was not found", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateMismatchUserId() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1_000L,
                "The Regulators",
                "Stephen King",
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book was not found", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateEmptyTitle() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "   ",
                "Stephen King",
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateNullTitle() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                null,
                "Stephen King",
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateEmptyAuthor() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "\t",
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("author is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateNullAuthor() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                null,
                "English",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("author is required", result.getMessages().get(0));
    }

    @Test
    void testShouldUpdateEmptyLanguage() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                "",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    @Test
    void testShouldUpdateNullLanguage() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                null,
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    @Test
    void testShouldNotUpdateZeroPages() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                0);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }

    @Test
    void testShouldNotUpdateNegativePages() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                "Stephen King",
                "English",
                -153);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }
}
