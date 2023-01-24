package com.bujo.bookshelf.book.controllers;

import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.services.BookService;
import com.bujo.bookshelf.response.Result;
import com.bujo.bookshelf.security.JwtConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * BookController is a class that handles creation, updates, and deletion of {@link Book} objects.
 *
 * @author skylar
 */
@RepositoryRestController
@ConditionalOnWebApplication
public class BookController {
    private final BookService service;
    private final JwtConverter jwtConverter;

    public BookController(BookService service, JwtConverter jwtConverter) {
        this.service = service;
        this.jwtConverter = jwtConverter;
    }

    /**
     * Deletes a {@link Book} by its id.
     *
     * @param id the id of the {@link Book} to be deleted.
     * @param token the JWT token passed in the request header, used for extracting user information.
     * @return an HTTP NO_CONTENT status if the deletion was successful.
     */
    @DeleteMapping("/books/{id}")
    public @ResponseBody ResponseEntity<Void> deleteBook(@PathVariable Long id,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        service.deleteById(id, jwtConverter.getAppUserIdClaimFromToken(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Creates a new {@link Book}.
     *
     * @param book the {@link Book} object to be created.
     * @return an HTTP CREATED status and the created {@link Book} object if the creation was successful.
     * Otherwise, an HTTP BAD_REQUEST status and error messages.
     */
    @PostMapping("/books")
    public @ResponseBody ResponseEntity<?> createBook(@RequestBody BookDTO book) {
        Result<BookDTO> result = service.create(book);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Updates a {@link Book} with the given id.
     *
     * @param book the {@link Book} to be updated
     * @return a response entity with HTTP status NO_CONTENT if update is successful,
     * or with a BAD_REQUEST status and the error messages if update is not successful.
     */
    @PutMapping("/books/{id}")
    public @ResponseBody ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        Result<BookDTO> result = service.update(book);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
