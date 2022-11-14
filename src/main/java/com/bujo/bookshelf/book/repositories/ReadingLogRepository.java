package com.bujo.bookshelf.book.repositories;

import com.bujo.bookshelf.book.models.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {
}
