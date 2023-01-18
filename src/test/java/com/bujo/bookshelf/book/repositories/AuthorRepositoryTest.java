package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test AuthorRepository Interface")
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findAll()}.
     */
    @Test
    @DisplayName("Should find all authors")
    void testShouldFindAll() {
        List<Author> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(5, actual.size());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#deleteById(Object)}.
     */
    @Test
    @DisplayName("Should delete Author by ID")
    void testShouldDeleteById() {
        repository.deleteById(5L);

        assertNull(repository.findById(5L).orElse(null));
        assertEquals(4, repository.findAll().size());
    }

    @Nested
    @ExtendWith(AuthorParameterResolver.class)
    @DisplayName("Test AppUserRepository find by ID")
    class AppUserRepositoryFindByIdTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findById(Object)}.
         */
        @Test
        @DisplayName("Should find by existing ID")
        void testShouldFindById(Author expected) {
            Author actual = repository.findById(1L).orElse(null);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findById(Object)}.
         */
        @Test
        @DisplayName("Should not find by ID that does not exist")
        void testShouldNotFindByMissingId() {
            Author actual = repository.findById(1_000_000L).orElse(null);

            assertNull(actual);
        }
    }

    @Nested
    @ExtendWith(AuthorParameterResolver.class)
    @DisplayName("Test AuthorRepository find by name")
    class AuthorRepositoryFindByNameTest {
        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findByName(String)}.
         */
        @Test
        @DisplayName("Should find by existing name")
        void testShouldFindByName(Author expected) {
            Author actual = repository.findByName(expected.getName());

            assertNotNull(actual);
            assertTrue(actual.getAuthorId() > 0);
            assertEquals(expected.getName(), actual.getName());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findByName(String)}.
         */
        @Test
        @DisplayName("Should not find by name that does not exist")
        void testShouldNotFindByMissingName() {
            Author actual = repository.findByName("Missing Author");

            assertNull(actual);
        }
    }

    @Nested
    @DisplayName("Test AuthorRepository save Author")
    class AuthorRepositorySaveTest {
        final String NEW_AUTHOR_NAME = "Stephen King";

        Author getNewAuthor() {
            Author newAuthor = new Author();
            newAuthor.setName(NEW_AUTHOR_NAME);
            return newAuthor;
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#save(Object)}.
         */
        @Test
        @DisplayName("Should save valid")
        void testShouldSave() {
            Author actual = repository.save(getNewAuthor());

            assertNotNull(actual);
            assertEquals(6, actual.getAuthorId());
            assertEquals(NEW_AUTHOR_NAME, actual.getName());
            assertTrue(actual.getBooks().isEmpty());
        }

        /**
         * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#save(Object)}.
         */
        @Test
        @DisplayName("Should not save with null name")
        void testShouldNotSaveNullAuthorName() {
            assertThrows(DataIntegrityViolationException.class, () -> repository.save(new Author()));
        }
    }
}
