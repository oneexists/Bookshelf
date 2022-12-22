package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookService {
    Result<BookDTO> create(BookDTO bookDto);

    Optional<Book> findById(Long bookId);

    @Transactional
    void deleteById(Long id, Long appUserId);

    Result<BookDTO> update(BookDTO book);
}
