package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Book;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class BookParameterResolver implements ParameterResolver {
    final String ENGLISH = "English";
    final String WAR_AND_PEACE = "War and Peace";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Book.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Book expected = new Book();
        expected.setTitle(WAR_AND_PEACE);
        expected.setLanguage(ENGLISH);
        expected.setPages(1296);

        return expected;
    }
}
