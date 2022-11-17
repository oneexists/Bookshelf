package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Author;
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
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository repository;
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
    void testShouldFindByName() {
        List<Author> actual = repository.findByName("Leo Tolstoy");

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void testShouldNotFindMissingName() {
        List<Author> actual = repository.findByName("Missing Author");

        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}