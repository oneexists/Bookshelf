package com.bujo.bookshelf.book.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class ReadingLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_log_id", nullable = false)
    private Long readingLogId;
    @ManyToOne
    private Book book;
    private LocalDate start;
    private LocalDate finish;

    public Long getReadingLogId() {
        return readingLogId;
    }

    public void setReadingLogId(Long readingLogId) {
        this.readingLogId = readingLogId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }
}
