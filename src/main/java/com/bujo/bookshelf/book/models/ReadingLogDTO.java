package com.bujo.bookshelf.book.models;

import java.time.LocalDate;

public record ReadingLogDTO(Long bookId, LocalDate start, LocalDate finish) {
}
