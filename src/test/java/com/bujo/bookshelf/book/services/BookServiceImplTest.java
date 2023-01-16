package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.AppUserRepository;
import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.repositories.AuthorRepository;
import com.bujo.bookshelf.book.repositories.BookRepository;
import com.bujo.bookshelf.book.validators.BookValidation;
import com.bujo.bookshelf.response.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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

    final String STEPHEN_KING = "Stephen King";
    final String KURT_VONNEGUT = "Kurt Vonnegut";
    final String RICHARD_BACHMAN = "Richard Bachman";

    AppUser appUser = new AppUser();
    Book hocusPocus = new Book();
    Book heartsInAtlantis = new Book();
    Book theRegulators = new Book();

    Map<String, Author> authors = new HashMap<>();

    @BeforeEach
    void setUp() {
        service = new BookServiceImpl(bookRepository, authorRepository, appUserRepository, validation);

        appUser.setAppUserId(1L);

        initializeAuthors();

        theRegulators = initializeBook(3L, authors.get(STEPHEN_KING), "The Regulators", 512);
        heartsInAtlantis = initializeBook(4L, authors.get(STEPHEN_KING), "Hearts in Atlantis", 640);
        hocusPocus = initializeBook(5L, authors.get(KURT_VONNEGUT), "Hocus Pocus", 322);

        authors.get(STEPHEN_KING).setBooks(Set.of(theRegulators, heartsInAtlantis));
        authors.get(KURT_VONNEGUT).setBooks(Set.of(hocusPocus));
    }

    private void initializeAuthors() {
        authors.put(STEPHEN_KING, initializeAuthor(4L, STEPHEN_KING));
        authors.put(KURT_VONNEGUT, initializeAuthor(5L, KURT_VONNEGUT));
        authors.put(RICHARD_BACHMAN, initializeAuthor(6L, RICHARD_BACHMAN));
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
        authors.get(STEPHEN_KING).setBooks(Set.of(heartsInAtlantis));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        given(bookRepository.save(any(Book.class))).willReturn(theRegulators);
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                STEPHEN_KING,
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
        theRegulators.setAuthor(authors.get(RICHARD_BACHMAN));
        given(authorRepository.findByName(authors.get(RICHARD_BACHMAN).getName())).willReturn(authors.get(RICHARD_BACHMAN));
        given(bookRepository.save(any(Book.class))).willReturn(theRegulators);
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                RICHARD_BACHMAN,
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

    @Test
    void testShouldNotCreateEmptyTitle() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                "\t",
                RICHARD_BACHMAN,
                theRegulators.getLanguage(),
                theRegulators.getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNullTitle() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                null,
                RICHARD_BACHMAN,
                theRegulators.getLanguage(),
                theRegulators.getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateZeroPages() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                RICHARD_BACHMAN,
                theRegulators.getLanguage(),
                0);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNegativePages() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                RICHARD_BACHMAN,
                theRegulators.getLanguage(),
                -22);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateEmptyAuthor() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                " ",
                theRegulators.getLanguage(),
                theRegulators.getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("author is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNullAuthor() {
        BookDTO bookDto = new BookDTO(
                theRegulators.getBookId(),
                appUser.getAppUserId(),
                theRegulators.getTitle(),
                null,
                theRegulators.getLanguage(),
                theRegulators.getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("author is required", result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    void testDeleteBookById() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        given(authorRepository.findById(authors.get(STEPHEN_KING).getAuthorId())).willReturn(Optional.of(authors.get(STEPHEN_KING)));
        ArgumentCaptor<Long> deleteBookByIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        service.deleteById(theRegulators.getBookId(), appUser.getAppUserId());
        verify(bookRepository).deleteById(deleteBookByIdArgCaptor.capture());

        assertThat(deleteBookByIdArgCaptor.getValue()).isEqualTo(theRegulators.getBookId());
        verify(authorRepository, never()).deleteById(any(Long.class));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    void testDeleteBookByIdAndDeleteAuthor() {
        given(bookRepository.findById(hocusPocus.getBookId())).willReturn(Optional.of(hocusPocus));
        given(authorRepository.findById(authors.get(KURT_VONNEGUT).getAuthorId())).willReturn(Optional.of(authors.get(KURT_VONNEGUT)));
        ArgumentCaptor<Long> deleteBookArgCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> deleteAuthorArgCaptor = ArgumentCaptor.forClass(Long.class);

        service.deleteById(hocusPocus.getBookId(), appUser.getAppUserId());
        verify(bookRepository).deleteById(deleteBookArgCaptor.capture());
        verify(authorRepository).deleteById(deleteAuthorArgCaptor.capture());

        assertThat(deleteBookArgCaptor.getValue()).isEqualTo(hocusPocus.getBookId());
        assertThat(deleteAuthorArgCaptor.getValue()).isEqualTo(authors.get(KURT_VONNEGUT).getAuthorId());
    }

    @Test
    void testDeleteBookByIdMissingId() {
        given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

        service.deleteById(1_000L, appUser.getAppUserId());

        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void testShouldNotDeleteMismatchUserId() {
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));

        service.deleteById(theRegulators.getBookId(), 1_000L);

        verify(bookRepository, never()).deleteById(any());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateAuthorOneBook() {
        given(authorRepository.findById(authors.get(KURT_VONNEGUT).getAuthorId())).willReturn(Optional.of(authors.get(KURT_VONNEGUT)));
        given(authorRepository.findByName(authors.get(KURT_VONNEGUT).getName())).willReturn(authors.get(KURT_VONNEGUT));
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

        assertThat(saveAuthorArgCaptor.getValue().getAuthorId()).isEqualTo(authors.get(KURT_VONNEGUT).getAuthorId());
        assertThat(saveAuthorArgCaptor.getValue().getName()).isEqualTo(bookDto.author());
        assertThat(saveBookArgCaptor.getValue().getPages()).isEqualTo(bookDto.pages());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateAuthorTwoBooks() {
        given(authorRepository.findById(authors.get(STEPHEN_KING).getAuthorId())).willReturn(Optional.of(authors.get(STEPHEN_KING)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        given(authorRepository.save(any(Author.class))).willReturn(authors.get(RICHARD_BACHMAN));
        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                RICHARD_BACHMAN,
                "English",
                512);

        service.update(bookDto);
        verify(authorRepository).save(saveAuthorArgCaptor.capture());
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveAuthorArgCaptor.getValue().getAuthorId()).isNotEqualTo(authors.get(STEPHEN_KING).getAuthorId());
        assertThat(saveAuthorArgCaptor.getValue().getName()).isEqualTo(bookDto.author());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(bookDto.author());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateBookToExistingAuthor() {
        theRegulators.setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(theRegulators));
        authors.get(STEPHEN_KING).setBooks(Set.of(heartsInAtlantis));

        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                STEPHEN_KING,
                "English",
                512);

        service.update(bookDto);
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveBookArgCaptor.getValue().getAuthor().getAuthorId()).isEqualTo(authors.get(STEPHEN_KING).getAuthorId());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(authors.get(STEPHEN_KING).getName());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testDeleteAuthorWithoutBooks() {
        theRegulators.setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(theRegulators));
        authors.get(STEPHEN_KING).setBooks(Set.of(heartsInAtlantis));

        given(bookRepository.findById(theRegulators.getBookId())).willReturn(Optional.of(theRegulators));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Long> deleteAuthorIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                "The Regulators",
                STEPHEN_KING,
                "English",
                512);

        service.update(bookDto);
        verify(authorRepository).deleteById(deleteAuthorIdArgCaptor.capture());

        assertEquals(authors.get(RICHARD_BACHMAN).getAuthorId(), deleteAuthorIdArgCaptor.getValue());
    }

    @Test
    void testShouldNotUpdateMissingBook() {
        given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

        BookDTO bookDto = new BookDTO(
                1_000L,
                1L,
                "The Regulators",
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
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
                STEPHEN_KING,
                "English",
                -153);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }
}
