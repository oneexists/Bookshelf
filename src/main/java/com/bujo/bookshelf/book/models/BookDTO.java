package com.bujo.bookshelf.book.models;

public record BookDTO(Long bookId, Long appUserId, String title, String author, String language, int pages) {
}
