package com.bujo.bookshelf.book.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Book Class")
class BookTest {
    Book book;
    ReadingLog readingLog;

    @BeforeEach
    void setup() {
        book = new Book();
        readingLog = new ReadingLog();
        readingLog.setBook(book);
        readingLog.setStart(LocalDate.of(2022, 11, 14));
    }

    /**
     * Test method for {@link Book#isInProgress()}.
     */
    @Test
    @DisplayName("Test Book is in progress")
    void testIsInProgress() {
        book.setReadingLogs(Set.of(readingLog));

        assertTrue(book.isInProgress());
    }

    /**
     * Test method for {@link Book#isInProgress()}.
     */
    @Test
    @DisplayName("Test Book is not in progress when has no reading logs")
    void testIsNotInProgressNoReadingLogs() {
        assertFalse(book.isInProgress());
    }

    /**
     * Test method for {@link Book#isInProgress()}.
     */
    @Test
    @DisplayName("Test Book is not in progress with no start or finish date")
    void testIsNotInProgressReadingLogNoDates() {
        readingLog.setStart(null);
        book.setReadingLogs(Set.of(readingLog));

        assertFalse(book.isInProgress());
    }

    /**
     * Test method for {@link Book#isInProgress()}.
     */
    @Test
    @DisplayName("Test Book is not in progress when reading logs have finish date")
    void testIsNotInProgressFinishedReadingLogs() {
        readingLog.setFinish(LocalDate.of(2022, 11, 21));
        book.setReadingLogs(Set.of(readingLog));

        assertFalse(book.isInProgress());
    }
}