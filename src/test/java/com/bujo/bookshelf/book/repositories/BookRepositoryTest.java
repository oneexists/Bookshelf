package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    BookRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void testShouldFindAll() {
        List<Book> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
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
        expected.setTitle("War and Peace");
        expected.setLanguage("English");
        expected.setPages(1296);

        return expected;
    }

    @Test
    void testShouldNotFindByMissingId() {
        Book actual = repository.findById(1_000_000L).orElse(null);

        assertNull(actual);
    }

    @Test
    void testShouldDeleteById() {
        repository.deleteById(2L);

        assertNull(repository.findById(2L).orElse(null));
    }
}