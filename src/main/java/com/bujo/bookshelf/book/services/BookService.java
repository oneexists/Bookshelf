package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.Result;

public interface BookService {
    Result<BookDTO> create(BookDTO bookDto);

    Result<BookDTO> update(BookDTO book);
}
