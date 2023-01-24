package com.bujo.bookshelf.book.controllers;

import com.bujo.bookshelf.book.models.ReadingLog;
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

/**
 * ReadingLogController manages the creation of {@link ReadingLog} objects.
 *
 * @author skylar
 */
@RepositoryRestController
@ConditionalOnWebApplication
public class ReadingLogController {
    private final ReadingLogService service;
    private final JwtConverter jwtConverter;

    public ReadingLogController(ReadingLogService service, JwtConverter jwtConverter) {
        this.service = service;
        this.jwtConverter = jwtConverter;
    }

    /**
     * Handles the creation of {@link ReadingLog} objects.
     *
     * @param readingLog the {@link ReadingLogDTO} containing the information for the {@link ReadingLog} to be created
     * @param token the JWT token passed in the request header, used for extracting user information.
     * @return a response containing the {@link ReadingLog} result
     * or with a BAD_REQUEST status and the error messages if the creation was not successful.
     */
    @PostMapping("/readingLogs")
    public @ResponseBody ResponseEntity<?> createReadingLog(@RequestBody ReadingLogDTO readingLog,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Result<ReadingLogDTO> result = service.create(readingLog, jwtConverter.getAppUserIdClaimFromToken(token));

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
