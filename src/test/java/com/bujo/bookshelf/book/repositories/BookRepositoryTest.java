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

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void testShouldFindAll() {
        List<Book> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }
}