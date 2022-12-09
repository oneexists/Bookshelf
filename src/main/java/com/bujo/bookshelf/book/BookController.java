package com.bujo.bookshelf.book;

import com.bujo.bookshelf.appUser.AppUserService;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.services.BookService;
import com.bujo.bookshelf.response.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@ConditionalOnWebApplication
public class BookController {
    private final BookService service;
    private final AppUserService appUserService;

    public BookController(BookService service, AppUserService appUserService) {
        this.service = service;
        this.appUserService = appUserService;
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