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
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Test BookServiceImpl Class")
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

    final String ENGLISH = "English";
    final String STEPHEN_KING = "Stephen King";
    final String KURT_VONNEGUT = "Kurt Vonnegut";
    final String RICHARD_BACHMAN = "Richard Bachman";
    final String THE_REGULATORS = "The Regulators";
    final String HEARTS_IN_ATLANTIS = "Hearts in Atlantis";
    final String HOCUS_POCUS = "Hocus Pocus";
    final String ERR_INVALID_APP_USER = "invalid app user";
    final String ERR_TITLE_REQUIRED = "title is required";
    final String ERR_AT_LEAST_ONE_PAGE_REQUIRED = "book must have at least one page";
    final String ERR_AUTHOR_REQUIRED = "author is required";
    final String ERR_BOOK_NOT_FOUND = "book was not found";

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
        newBook.setLanguage(ENGLISH);
        newBook.setPages(pages);
        return newBook;
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findById(Long)}.
     */
    @Test
    @DisplayName("Should find Book by existing ID")
    void testShouldFindByExistingId() {
        given(bookRepository.findById(4L)).willReturn(Optional.of(books.get(HEARTS_IN_ATLANTIS)));

        assertNotNull(service.findById(4L).orElse(null));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findById(Long)}.
     */
    @Test
    @DisplayName("Should not find Book by ID that does not exist")
    void testShouldNotFindMissingId() {
        assertEquals(Optional.empty(), service.findById(1L));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should create Book with existing Author")
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
                ENGLISH,
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
    @DisplayName("Should create Book with new Author")
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
                ENGLISH,
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
    @DisplayName("Should not create book for AppUser that does not exist")
    void testShouldNotCreateBookWithMissingAppUser() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                1_000L,
                THE_REGULATORS,
                RICHARD_BACHMAN,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_INVALID_APP_USER, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with empty title")
    void testShouldNotCreateEmptyTitle() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                "\t",
                RICHARD_BACHMAN,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_TITLE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with null title")
    void testShouldNotCreateNullTitle() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                null,
                RICHARD_BACHMAN,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_TITLE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with zero pages")
    void testShouldNotCreateZeroPages() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                ENGLISH,
                0);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AT_LEAST_ONE_PAGE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with negative pages")
    void testShouldNotCreateNegativePages() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                ENGLISH,
                -22);

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AT_LEAST_ONE_PAGE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with empty Author name")
    void testShouldNotCreateEmptyAuthor() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                " ",
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AUTHOR_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
     */
    @Test
    @DisplayName("Should not create Book with null Author name")
    void testShouldNotCreateNullAuthor() {
        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                null,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.create(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AUTHOR_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    @DisplayName("Should delete Book by ID")
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
    @DisplayName("Should delete Author when only Book is deleted")
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

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    @DisplayName("Should not delete Book if Book ID does not exist")
    void testDeleteBookByIdMissingId() {
        given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

        service.deleteById(1_000L, appUser.getAppUserId());

        verify(bookRepository, never()).deleteById(any());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
     */
    @Test
    @DisplayName("Should not delete Book without matching AppUser ID")
    void testShouldNotDeleteMismatchUserId() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        service.deleteById(books.get(THE_REGULATORS).getBookId(), 1_000L);

        verify(bookRepository, never()).deleteById(any());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should update Author name for Author with one Book")
    void testUpdateAuthorOneBook() {
        given(authorRepository.findById(authors.get(KURT_VONNEGUT).getAuthorId())).willReturn(Optional.of(authors.get(KURT_VONNEGUT)));
        given(authorRepository.findByName(authors.get(KURT_VONNEGUT).getName())).willReturn(authors.get(KURT_VONNEGUT));
        given(bookRepository.findById(books.get(HOCUS_POCUS).getBookId())).willReturn(Optional.of(books.get(HOCUS_POCUS)));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                books.get(HOCUS_POCUS).getBookId(),
                appUser.getAppUserId(),
                HOCUS_POCUS,
                "Kurt Vonnegut Jr.",
                ENGLISH,
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
    @DisplayName("Should create new Author if Author has more than one Book")
    void testUpdateAuthorTwoBooks() {
        given(authorRepository.findById(authors.get(STEPHEN_KING).getAuthorId())).willReturn(Optional.of(authors.get(STEPHEN_KING)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        given(authorRepository.save(any(Author.class))).willReturn(authors.get(RICHARD_BACHMAN));
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        ArgumentCaptor<Author> saveAuthorArgCaptor = ArgumentCaptor.forClass(Author.class);
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                RICHARD_BACHMAN,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

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
    @DisplayName("Should update Author of Book to existing Author")
    void testUpdateBookToExistingAuthor() {
        books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(books.get(THE_REGULATORS)));
        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));

        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Book> saveBookArgCaptor = ArgumentCaptor.forClass(Book.class);

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        service.update(bookDto);
        verify(bookRepository).saveAndFlush(saveBookArgCaptor.capture());

        assertThat(saveBookArgCaptor.getValue().getAuthor().getAuthorId()).isEqualTo(authors.get(STEPHEN_KING).getAuthorId());
        assertThat(saveBookArgCaptor.getValue().getAuthor().getName()).isEqualTo(authors.get(STEPHEN_KING).getName());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should delete Author if only Book is deleted")
    void testDeleteAuthorWithoutBooks() {
        books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
        authors.get(RICHARD_BACHMAN).setBooks(Set.of(books.get(THE_REGULATORS)));
        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));

        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
        given(authorRepository.findById(authors.get(RICHARD_BACHMAN).getAuthorId())).willReturn(Optional.of(authors.get(RICHARD_BACHMAN)));
        given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
        ArgumentCaptor<Long> deleteAuthorIdArgCaptor = ArgumentCaptor.forClass(Long.class);

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        service.update(bookDto);
        verify(authorRepository).deleteById(deleteAuthorIdArgCaptor.capture());

        assertEquals(authors.get(RICHARD_BACHMAN).getAuthorId(), deleteAuthorIdArgCaptor.getValue());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book if does not exist")
    void testShouldNotUpdateMissingBook() {
        given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

        BookDTO bookDto = new BookDTO(
                1_000L,
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_BOOK_NOT_FOUND, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book if AppUser ID does not match")
    void testShouldNotUpdateMismatchUserId() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                1_000L,
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_BOOK_NOT_FOUND, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with empty title")
    void testShouldNotUpdateEmptyTitle() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                "   ",
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_TITLE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with null title")
    void testShouldNotUpdateNullTitle() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                null,
                STEPHEN_KING,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_TITLE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with empty Author name")
    void testShouldNotUpdateEmptyAuthor() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                "\t",
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AUTHOR_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with null Author name")
    void testShouldNotUpdateNullAuthor() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                null,
                ENGLISH,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AUTHOR_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should update Book with empty language")
    void testShouldUpdateEmptyLanguage() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                "",
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should update Book with null language")
    void testShouldUpdateNullLanguage() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                null,
                books.get(THE_REGULATORS).getPages());

        Result<BookDTO> result = service.update(bookDto);

        assertTrue(result.isSuccess());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with zero pages")
    void testShouldNotUpdateZeroPages() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                0);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AT_LEAST_ONE_PAGE_REQUIRED, result.getMessages().get(0));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
     */
    @Test
    @DisplayName("Should not update Book with negative pages")
    void testShouldNotUpdateNegativePages() {
        given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

        BookDTO bookDto = new BookDTO(
                books.get(THE_REGULATORS).getBookId(),
                appUser.getAppUserId(),
                THE_REGULATORS,
                STEPHEN_KING,
                ENGLISH,
                -153);

        Result<BookDTO> result = service.update(bookDto);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals(ERR_AT_LEAST_ONE_PAGE_REQUIRED, result.getMessages().get(0));
    }
}
