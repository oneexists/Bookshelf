package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.book.models.ReadingLogDTO;
import com.bujo.bookshelf.response.Result;

public interface ReadingLogService {
    Result<ReadingLogDTO> create(ReadingLogDTO readingLogDto, Long appUserId);
}
