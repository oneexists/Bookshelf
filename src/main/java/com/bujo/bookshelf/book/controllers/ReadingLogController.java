package com.bujo.bookshelf.book.controllers;

import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.book.services.ReadingLogService;
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
public class ReadingLogController {
    private final ReadingLogService service;
    private final JwtConverter jwtConverter;

    public ReadingLogController(ReadingLogService service, JwtConverter jwtConverter) {
        this.service = service;
        this.jwtConverter = jwtConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/readingLogs")
    public @ResponseBody ResponseEntity<?> createReadingLog(@RequestBody ReadingLogDTO readingLog,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Result<ReadingLogDTO> result = service.create(readingLog, jwtConverter.getAppUserIdClaimFromToken(token));

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
