package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.appUser.AppUserRepository;
import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test BookRepository Interface")
class BookRepositoryTest {
    @Autowired
    BookRepository repository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findAll()}.
     */
    @Test
    @DisplayName("Should find all books")
    void testShouldFindAll() {
        List<Book> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(5, actual.size());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#deleteById(Object)}.
     */
    @Test
    @DisplayName("Should delete Book by ID")
    void testShouldDeleteById() {
        repository.deleteById(4L);

        assertNull(repository.findById(4L).orElse(null));
        assertEquals(4, repository.findAll().size());
    }

    @Nested
    @ExtendWith(BookParameterResolver.class)
    @DisplayName("Test BookRepository find by ID")
    class BookRepositoryFindByIdTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findById(Object)}.
         */
        @Test
        @DisplayName("Should find Book by existing ID")
        void testShouldFindById(Book expected) {
            Book actual = repository.findById(1L).orElse(null);

            assertNotNull(actual);
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getLanguage(), actual.getLanguage());
            assertEquals(expected.getPages(), actual.getPages());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findById(Object)}.
         */
        @Test
        @DisplayName("Should not find Book by ID that does not exist")
        void testShouldNotFindByMissingId() {
            Book actual = repository.findById(1_000_000L).orElse(null);

            assertNull(actual);
        }
    }

    @Nested
    @DisplayName("Test BookRepository find books by user")
    class BookRepositoryFindByUserTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findByUser(AppUser)}.
         */
        @Test
        @DisplayName("Should find books by existing AppUser")
        void testShouldFindAppUserBooks() {
            AppUser appUser = appUserRepository.findById(1L).orElse(null);
            Set<Book> actual = repository.findByUser(appUser);

            assertNotNull(actual);
            assertFalse(actual.isEmpty());
            assertEquals(5, actual.size());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findByUser(AppUser)}.
         */
        @Test
        @DisplayName("Should find no books by existing AppUser")
        void testShouldFindNoAppUserBooks() {
            AppUser appUser = appUserRepository.findById(2L).orElse(null);
            Set<Book> actual = repository.findByUser(appUser);

            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findByUser(AppUser)}.
         */
        @Test
        @DisplayName("Should find no books by missing AppUser")
        void testShouldNotFindBooksMissingAppUser() {
            Set<Book> actual = repository.findByUser(null);

            assertNotNull(actual);
            assertTrue(actual.isEmpty());
        }
    }
}