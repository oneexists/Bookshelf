package com.bujo.bookshelf.book.models;

public record BookDTO(Long bookId, Long appUserId, String title, String author, String language, int pages) {
    public static BookDTO fromBook(Book book) {
        return new BookDTO(
                book.getBookId(),
                book.getUser().getAppUserId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getLanguage(),
                book.getPages()
        );
    }
}
