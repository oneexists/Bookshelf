package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.ReadingLog;
import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.response.Result;

public interface ReadingLogService {
    /**
     * Creates a new {@link ReadingLog}.
     *
     * @param readingLogDto the DTO containing the information for the new {@link ReadingLog}
     * @param appUserId the ID of the {@link AppUser} creating the {@link ReadingLog}
     * @return the result of the create operation, containing the newly created {@link ReadingLog} if successful
     */
    Result<ReadingLogDTO> create(ReadingLogDTO readingLogDto, Long appUserId);
}
