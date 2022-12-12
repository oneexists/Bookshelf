package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.Result;
import org.springframework.transaction.annotation.Transactional;

public interface BookService {
    Result<BookDTO> create(BookDTO bookDto);

    @Transactional
    boolean deleteById(Long id, Long appUserId);

    Result<BookDTO> update(BookDTO book);
}
