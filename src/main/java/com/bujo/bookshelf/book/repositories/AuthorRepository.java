package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @RestResource(path = "names", rel = "names")
    List<Author> findByName(@Param("name") String name);
}
