package com.bujo.bookshelf.book.models;

import com.bujo.bookshelf.appUser.models.AppUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long bookId;
    @ManyToOne
    private AppUser user;
    private String title;
    @ManyToOne
    private Author author;
    private String language;
    private int pages;
    @OneToMany(mappedBy = "book")
    private Set<ReadingLog> readingLogs;

    public boolean isInProgress() {
        if (readingLogs == null) {
            return false;
        }
        return readingLogs.stream().anyMatch(readingLog -> readingLog.getFinish() == null);
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Set<ReadingLog> getReadingLogs() {
        return readingLogs;
    }

    public void setReadingLogs(Set<ReadingLog> readingLogs) {
        this.readingLogs = readingLogs;
    }
}
