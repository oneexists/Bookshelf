package com.bujo.bookshelf.book.controllers;

import com.bujo.bookshelf.appUser.models.AppUserDetails;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.services.BookService;
import com.bujo.bookshelf.response.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@ConditionalOnWebApplication
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/books/{id}")
    public @ResponseBody ResponseEntity<Void> deleteBook(@PathVariable Long id,
                                                         @AuthenticationPrincipal AppUserDetails appUser) {
        service.deleteById(id, appUser.getAppUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
