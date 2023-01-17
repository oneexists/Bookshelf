package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test BookRepository Interface")
class BookRepositoryTest {
    @Autowired
    BookRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    final String ENGLISH = "English";
    final String WAR_AND_PEACE = "War and Peace";

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
     * Test method for {@link com.bujo.bookshelf.book.repositories.BookRepository#findById(Object)}.
     */
    @Test
    @DisplayName("Should find Book by existing ID")
    void testShouldFindById() {
        Book expected = getExpectedBook();
        Book actual = repository.findById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getLanguage(), actual.getLanguage());
        assertEquals(expected.getPages(), actual.getPages());
    }

    Book getExpectedBook() {
        Book expected = new Book();
        expected.setTitle(WAR_AND_PEACE);
        expected.setLanguage(ENGLISH);
        expected.setPages(1296);

        return expected;
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
}