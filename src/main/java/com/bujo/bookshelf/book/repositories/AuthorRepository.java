package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
