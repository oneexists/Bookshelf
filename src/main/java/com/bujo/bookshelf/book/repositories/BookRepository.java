package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.projections.InlineAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlineAuthor.class)
public interface BookRepository extends JpaRepository<Book, Long> {
}
