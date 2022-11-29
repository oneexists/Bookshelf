package com.bujo.bookshelf.book.models.projections;

import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineAuthor", types= { Book.class })
public interface InlineAuthor {
    Long getBookId();
    String getTitle();
    String getLanguage();
    int getPages();
    Author getAuthor();
}
