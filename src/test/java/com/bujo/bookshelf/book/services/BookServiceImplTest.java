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
    final String THE_REGULATORS = "The Regulators";
    final String HEARTS_IN_ATLANTIS = "Hearts in Atlantis";
    final String HOCUS_POCUS = "Hocus Pocus";

    AppUser appUser = new AppUser();

    Map<String, Author> authors = new HashMap<>();
    Map<String, Book> books = new HashMap<>();

    @BeforeEach
    void setUp() {
        service = new BookServiceImpl(bookRepository, authorRepository, appUserRepository, validation);

        appUser.setAppUserId(1L);

        initializeAuthors();
        initializeBooks();

        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(THE_REGULATORS), books.get(HEARTS_IN_ATLANTIS)));
        authors.get(KURT_VONNEGUT).setBooks(Set.of(books.get(HOCUS_POCUS)));
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

    private void initializeBooks() {
        books.put(THE_REGULATORS, initializeBook(3L, authors.get(STEPHEN_KING), THE_REGULATORS, 512));
        books.put(HEARTS_IN_ATLANTIS, initializeBook(4L, authors.get(STEPHEN_KING), HEARTS_IN_ATLANTIS, 640));
        books.put(HOCUS_POCUS, initializeBook(5L, authors.get(KURT_VONNEGUT), HOCUS_POCUS, 322));
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
        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        given(bookRepository.save(any(Book.class))).willReturn(books.get(THE_REGULATORS));
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

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
        books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
        given(authorRepository.findByName(authors.get(RICHARD_BACHMAN).getName())).willReturn(authors.get(RICHARD_BACHMAN));
        given(bookRepository.save(any(Book.class))).willReturn(books.get(THE_REGULATORS));
        given(appUserRepository.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
        ArgumentCaptor<Book> newBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

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
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                "\t",
                RICHARD_BACHMAN,
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNullTitle() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                null,
                RICHARD_BACHMAN,
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("title is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateZeroPages() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                books.get(THE_REGULATORS).getLanguage(),
                0);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNegativePages() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                books.get(THE_REGULATORS).getLanguage(),
                -22);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateEmptyAuthor() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                " ",
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("author is required", result.getMessages().get(0));
    }

    @Test
    void testShouldNotCreateNullAuthor() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                null,
                books.get(THE_REGULATORS).getLanguage(),
                books.get(THE_REGULATORS).getPages());

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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        given(authorRepository.findById(authors.get(STEPHEN_KING).getAuthorId())).willReturn(Optional.of(authors.get(STEPHEN_KING)));
        ArgumentCaptor<Long> deleteBookByIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        service.deleteById(books.get(THE_REGULATORS).getBookId(), appUser.getAppUserId());
        verify(bookRepository).deleteById(deleteBookByIdArgCaptor.capture());

        assertThat(deleteBookByIdArgCaptor.getValue()).isEqualTo(books.get(THE_REGULATORS).getBookId());
        verify(authorRepository, never()).deleteById(any(Long.class));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    void testDeleteBookByIdAndDeleteAuthor() {
        given(bookRepository.findById(books.get(HOCUS_POCUS).getBookId())).willReturn(Optional.of(books.get(HOCUS_POCUS)));
        given(authorRepository.findById(authors.get(KURT_VONNEGUT).getAuthorId())).willReturn(Optional.of(authors.get(KURT_VONNEGUT)));
        ArgumentCaptor<Long> deleteBookArgCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> deleteAuthorArgCaptor = ArgumentCaptor.forClass(Long.class);

        service.deleteById(books.get(HOCUS_POCUS).getBookId(), appUser.getAppUserId());
        verify(bookRepository).deleteById(deleteBookArgCaptor.capture());
        verify(authorRepository).deleteById(deleteAuthorArgCaptor.capture());

        assertThat(deleteBookArgCaptor.getValue()).isEqualTo(books.get(HOCUS_POCUS).getBookId());
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        service.deleteById(books.get(THE_REGULATORS).getBookId(), 1_000L);

        verify(bookRepository, never()).deleteById(any());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    void testUpdateAuthorOneBook() {
        given(authorRepository.findById(authors.get(KURT_VONNEGUT).getAuthorId())).willReturn(Optional.of(authors.get(KURT_VONNEGUT)));
        given(authorRepository.findByName(authors.get(KURT_VONNEGUT).getName())).willReturn(authors.get(KURT_VONNEGUT));
        given(bookRepository.findById(books.get(HOCUS_POCUS).getBookId())).willReturn(Optional.of(books.get(HOCUS_POCUS)));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                5L,
                1L,
                HOCUS_POCUS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
        books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(books.get(THE_REGULATORS)));
        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));

        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
        books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(books.get(THE_REGULATORS)));
        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));

        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Long> deleteAuthorIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
                THE_REGULATORS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1_000L,
                THE_REGULATORS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
                STEPHEN_KING,
                "",
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    @Test
    void testShouldUpdateNullLanguage() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
                STEPHEN_KING,
                null,
                512);

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    @Test
    void testShouldNotUpdateZeroPages() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
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
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                3L,
                1L,
                THE_REGULATORS,
                STEPHEN_KING,
                "English",
                -153);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("book must have at least one page", result.getMessages().get(0));
    }
}
