package com.bujo.bookshelf.book;

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

@RepositoryRestController
@ConditionalOnWebApplication
public class BookController {
    private final BookService service;
    private final JwtConverter jwtConverter;

    public BookController(BookService service, JwtConverter jwtConverter) {
        this.service = service;
        this.jwtConverter = jwtConverter;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/books/{id}")
    public @ResponseBody ResponseEntity<Void> deleteBook(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (service.deleteById(id, jwtConverter.getUserFromToken(token).getAppUserId())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/books")
    public @ResponseBody ResponseEntity<?> createBook(@RequestBody BookDTO book) {
        Result<BookDTO> result = service.create(book);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/books/{id}")
    public @ResponseBody ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        Result<BookDTO> result = service.update(book);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
