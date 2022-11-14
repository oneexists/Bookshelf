package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
