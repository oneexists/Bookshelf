package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    final String AUTHOR_NAME = "Leo Tolstoy";
    final String NEW_AUTHOR_NAME = "Stephen King";

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
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findById(Object)}.
     */
    @Test
    @DisplayName("Should find Author by existing ID")
    void testShouldFindById() {
        Author actual = repository.findById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(AUTHOR_NAME, actual.getName());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findById(Object)}.
     */
    @Test
    @DisplayName("Should not find Author by ID that does not exist")
    void testShouldNotFindByMissingId() {
        Author actual = repository.findById(1_000_000L).orElse(null);

        assertNull(actual);
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findByName(String)}.
     */
    @Test
    @DisplayName("Should find Author by existing name")
    void testShouldFindByName() {
        Author actual = repository.findByName(AUTHOR_NAME);

        assertNotNull(actual);
        assertTrue(actual.getAuthorId() > 0);
        assertEquals(AUTHOR_NAME, actual.getName());
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#findByName(String)}.
     */
    @Test
    @DisplayName("Should not find Author by name that does not exist")
    void testShouldNotFindByMissingName() {
        Author actual = repository.findByName("Missing Author");

        assertNull(actual);
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#save(Object)}.
     */
    @Test
    @DisplayName("Should save valid Author")
    void testShouldSave() {
        Author actual = repository.save(getNewAuthor());

        assertNotNull(actual);
        assertEquals(6, actual.getAuthorId());
        assertEquals(NEW_AUTHOR_NAME, actual.getName());
        assertTrue(actual.getBooks().isEmpty());
    }

    Author getNewAuthor() {
        Author newAuthor = new Author();
        newAuthor.setName(NEW_AUTHOR_NAME);
        return newAuthor;
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#save(Object)}.
     */
    @Test
    @DisplayName("Should not save Author with null name")
    void testShouldNotSaveNullAuthorName() {
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(new Author()));
    }

    /**
     * Test method for {@link com.bujo.bookshelf.book.repositories.AuthorRepository#deleteById(Object)}.
     */
    @Test
    @DisplayName("Should delete Author by ID")
    void testShouldDeleteById() {
        repository.deleteById(2L);

        assertNull(repository.findById(2L).orElse(null));
    }
}
