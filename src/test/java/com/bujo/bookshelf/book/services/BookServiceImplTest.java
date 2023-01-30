package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.AppUserService;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.bujo.bookshelf.book.services.InitUtility.*;
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
    AppUserService appUserService;
    @Autowired
    BookValidation validation;

    final String ERR_TITLE_REQUIRED = "title is required";
    final String ERR_AT_LEAST_ONE_PAGE_REQUIRED = "book must have at least one page";
    final String ERR_AUTHOR_REQUIRED = "author is required";

    AppUser appUser = getAppUser();

    Map<String, Author> authors = getAuthors();
    Map<String, Book> books = getBooks();

    @BeforeEach
    void setUp() {
        service = new BookServiceImpl(bookRepository, authorRepository, appUserService, validation);

        authors.get(STEPHEN_KING).setBooks(Set.of(books.get(THE_REGULATORS), books.get(HEARTS_IN_ATLANTIS)));
        authors.get(KURT_VONNEGUT).setBooks(Set.of(books.get(HOCUS_POCUS)));
    }

    @Nested
    @DisplayName("Test BookServiceImpl find books by AppUser")
    class BookServiceImplFindByUserTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findByUser(AppUser)}.
         */
        @Test
        void testShouldFindByAppUser() {
            given(appUserService.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
            given(bookRepository.findByUser(appUser)).willReturn(new HashSet<>(books.values()));

            Set<Book> result = service.findByUser(appUser);

            assertNotNull(result);
            assertEquals(books.values().size(), result.size());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findByUser(AppUser)}.
         */
        @Test
        void testShouldFindNoAppUserBooks() {
            given(appUserService.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
            Set<Book> result = service.findByUser(appUser);

            assertNotNull(result);
            assertEquals(0, result.size());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findByUser(AppUser)}.
         */
        @Test
        void testShouldNotFindBooksMissingAppUser() {
            assertNull(service.findByUser(new AppUser()));
        }
    }

    @Nested
    @DisplayName("Test BookServiceImpl find Book by ID")
    class BookServiceImplFindByIdTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findById(Long)}.
         */
        @Test
        @DisplayName("Should find by existing ID")
        void testShouldFindByExistingId() {
            given(bookRepository.findById(4L)).willReturn(Optional.of(books.get(HEARTS_IN_ATLANTIS)));

            assertNotNull(service.findById(4L).orElse(null));
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#findById(Long)}.
         */
        @Test
        @DisplayName("Should not find by ID that does not exist")
        void testShouldNotFindMissingId() {
            assertEquals(Optional.empty(), service.findById(1L));
        }
    }

    @Nested
    @DisplayName("Test BookServiceImpl create")
    class BookServiceImplCreateTest {
        final String ERR_INVALID_APP_USER = "invalid app user";

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#create(BookDTO)}.
         */
        @Test
        @DisplayName("Should create with existing Author")
        void testCreateBookExistingAuthor() {
            authors.get(STEPHEN_KING).setBooks(Set.of(books.get(HEARTS_IN_ATLANTIS)));
            given(authorRepository.findByName(authors.get(STEPHEN_KING).getName())).willReturn(authors.get(STEPHEN_KING));
            given(bookRepository.save(any(Book.class))).willReturn(books.get(THE_REGULATORS));
            given(appUserService.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
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
        @DisplayName("Should create with new Author")
        void testCreateBookNewAuthor() {
            books.get(THE_REGULATORS).setAuthor(authors.get(RICHARD_BACHMAN));
            given(authorRepository.findByName(authors.get(RICHARD_BACHMAN).getName())).willReturn(authors.get(RICHARD_BACHMAN));
            given(bookRepository.save(any(Book.class))).willReturn(books.get(THE_REGULATORS));
            given(appUserService.findById(appUser.getAppUserId())).willReturn(Optional.of(appUser));
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
        @DisplayName("Should not create for AppUser that does not exist")
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
        @DisplayName("Should not create with empty title")
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
        @DisplayName("Should not create with null title")
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
        @DisplayName("Should not create with zero pages")
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
        @DisplayName("Should not create with negative pages")
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
        @DisplayName("Should not create with empty Author name")
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
        @DisplayName("Should not create with null Author name")
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
    }

    @Nested
    @DisplayName("Test BookServiceImpl delete by ID")
    class BookServiceImplDeleteByIdTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
         */
        @Test
        @DisplayName("Should delete by ID")
        void testDeleteBookById() {
            books.get(THE_REGULATORS).setAuthor(authors.get(STEPHEN_KING));
            given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));
            given(authorRepository.findById(authors.get(STEPHEN_KING).getAuthorId())).willReturn(Optional.of(authors.get(STEPHEN_KING)));
            ArgumentCaptor<Long> deleteBookByIdArgCaptor = ArgumentCaptor.forClass(Long.class);

            System.out.println(authors.get(STEPHEN_KING).getBooks());

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
        @DisplayName("Should not delete if Book ID does not exist")
        void testDeleteBookByIdMissingId() {
            given(bookRepository.findById(any(Long.class))).willReturn(Optional.empty());

            service.deleteById(1_000L, appUser.getAppUserId());

            verify(bookRepository, never()).deleteById(any());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#deleteById(Long, Long)}.
         */
        @Test
        @DisplayName("Should not delete without matching AppUser ID")
        void testShouldNotDeleteMismatchUserId() {
            given(bookRepository.findById(books.get(THE_REGULATORS).getBookId())).willReturn(Optional.of(books.get(THE_REGULATORS)));

            service.deleteById(books.get(THE_REGULATORS).getBookId(), 1_000L);

            verify(bookRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Test BookServiceImpl update")
    class BookServiceImplUpdateTest {
        final String ERR_BOOK_NOT_FOUND = "book was not found";

        /**
         * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
         */
        @Test
        @DisplayName("Should not update if does not exist")
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
        @DisplayName("Should not update if AppUser ID does not match")
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

        @Nested
        @DisplayName("Test BookServiceImpl update Author")
        class BookServiceImplUpdateAuthorTest {
            /**
             * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
             */
            @Test
            @DisplayName("Should not update with empty Author name")
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
            @DisplayName("Should not update with null Author name")
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
            @DisplayName("Should update Book and delete Author without books")
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
        }

        @Nested
        @DisplayName("Test BookServiceImpl update title")
        class BookServiceImplUpdateTitleTest {
            /**
             * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
             */
            @Test
            @DisplayName("Should not update with empty title")
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
            @DisplayName("Should not update with null title")
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
        }

        @Nested
        @DisplayName("Test BookServiceImpl update language")
        class BookServiceImplUpdateLanguageTest {
            /**
             * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
             */
            @Test
            @DisplayName("Should update with empty language")
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
            @DisplayName("Should update with null language")
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
        }

        @Nested
        @DisplayName("Test BookServiceImpl update pages")
        class BookServiceImplUpdatePagesTest {
            /**
             * Test method for {@link com.bujo.bookshelf.book.services.BookServiceImpl#update(BookDTO)}.
             */
            @Test
            @DisplayName("Should not update with zero pages")
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
            @DisplayName("Should not update with negative pages")
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
    }
}
