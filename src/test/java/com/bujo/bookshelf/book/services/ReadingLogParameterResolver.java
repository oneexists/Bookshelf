package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.ReadingLog;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ReadingLogParameterResolver implements ParameterResolver {
    private final Author stephenKing = initializeStephenKing();
    private final AppUser appUser = initializeAppUser();
    private final Book heartsInAtlantis = initializeHeartsInAtlantis();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ReadingLog.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ReadingLog heartsInAtlantisLog = new ReadingLog();
        heartsInAtlantisLog.setBook(heartsInAtlantis);
        return heartsInAtlantisLog;
    }

    private Author initializeStephenKing() {
        Author newAuthor = new Author();
        newAuthor.setAuthorId(4L);
        newAuthor.setName("Stephen King");
        return newAuthor;
    }

    private AppUser initializeAppUser() {
        AppUser appUser = new AppUser();
        appUser.setAppUserId(1L);
        return appUser;
    }

    private Book initializeHeartsInAtlantis() {
        Book newBook = new Book();
        newBook.setUser(appUser);
        newBook.setBookId(1L);
        newBook.setAuthor(stephenKing);
        newBook.setTitle("Hearts in Atlantis");
        newBook.setLanguage("English");
        newBook.setPages(640);
        return newBook;
    }
}
