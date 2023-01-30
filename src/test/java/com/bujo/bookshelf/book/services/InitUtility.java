package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;

import java.util.Map;

public class InitUtility {
    static final String ENGLISH = "English";
    static final String STEPHEN_KING = "Stephen King";
    static final String KURT_VONNEGUT = "Kurt Vonnegut";
    static final String RICHARD_BACHMAN = "Richard Bachman";
    static final String THE_REGULATORS = "The Regulators";
    static final String HEARTS_IN_ATLANTIS = "Hearts in Atlantis";
    static final String HOCUS_POCUS = "Hocus Pocus";

    private static final Map<String, Author> authors = Map.of(
            STEPHEN_KING, initializeAuthor(4L, STEPHEN_KING),
            KURT_VONNEGUT, initializeAuthor(5L, KURT_VONNEGUT),
            RICHARD_BACHMAN, initializeAuthor(6L, RICHARD_BACHMAN)
    );
    private static final Map<String, Book> books = Map.of(
            THE_REGULATORS, initializeBook(3L, authors.get(STEPHEN_KING), THE_REGULATORS, 512),
            HEARTS_IN_ATLANTIS, initializeBook(4L, authors.get(STEPHEN_KING), HEARTS_IN_ATLANTIS, 640),
            HOCUS_POCUS, initializeBook(5L, authors.get(KURT_VONNEGUT), HOCUS_POCUS, 322)
    );

    static AppUser getAppUser() {
        AppUser appUser = new AppUser();
        appUser.setAppUserId(1L);
        return appUser;
    }

    static Map<String, Author> getAuthors() {
        return Map.copyOf(authors);
    }

    private static Author initializeAuthor(Long id, String name) {
        Author newAuthor = new Author();
        newAuthor.setAuthorId(id);
        newAuthor.setName(name);
        return newAuthor;
    }

    static Map<String, Book> getBooks() {
        return Map.copyOf(books);
    }

    private static Book initializeBook(Long id, Author author, String title, int pages) {
        Book newBook = new Book();
        newBook.setUser(getAppUser());
        newBook.setBookId(id);
        newBook.setAuthor(author);
        newBook.setTitle(title);
        newBook.setLanguage(ENGLISH);
        newBook.setPages(pages);
        return newBook;
    }
}
