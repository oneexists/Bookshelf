package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.models.ReadingLog;
import com.bujo.bookshelf.response.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface BookService {
    /**
     * Find a user's in progress books, any books with a {@link ReadingLog} that has a start date
     * and no finish date.
     *
     * @param appUserId the ID of the {@link AppUser} to search by
     * @return the set of {@link BookDTO} that corresponds to in progress books
     */
    Set<BookDTO> findInProgress(Long appUserId);

    /**
     * Creates a new {@link Book}.
     *
     * @param bookDto the DTO containing the information for the new {@link Book}
     * @return the result of the create operation, containing the newly created {@link Book} if successful
     */
    Result<BookDTO> create(BookDTO bookDto);

    /**
     * Find a user's finished books, any books with a {@link ReadingLog} that has a finish date.
     *
     * @param appUserId the ID of the {@link AppUser} to search by
     * @return the set of {@link BookDTO} that corresponds to finished books
     */
    Set<BookDTO> findRead(Long appUserId);

    /**
     * Find a user's unread books, any book without a {@link ReadingLog}.
     *
     * @param appUserId the ID of the {@link AppUser} to search by
     * @return the set of {@link BookDTO} that corresponds to unread books
     */
    Set<BookDTO> findUnread(Long appUserId);

    /**
     * Find books by {@link AppUser}.
     *
     * @param appUser the {@link AppUser} to search by
     * @return the set of {@link Book} objects created by the {@link AppUser}
     */
    Set<Book> findByUser(AppUser appUser);

    /**
     * Finds a {@link Book} by its ID.
     *
     * @param bookId the ID of the {@link Book} to be found
     * @return an {@link Optional} of the {@link Book} result of the find operation
     */
    Optional<Book> findById(Long bookId);

    /**
     * Delete a {@link Book} by its ID.
     *
     * @param id the ID of the {@link Book} to be deleted
     * @param appUserId the ID of the {@link AppUser} requesting to delete the {@link Book}
     */
    @Transactional
    void deleteById(Long id, Long appUserId);

    /**
     * Updates a {@link Book}.
     *
     * @param book the DTO of the {@link Book} to be updated
     * @return the result of the update operation, containing the updated {@link BookDTO} if successful
     */
    Result<BookDTO> update(BookDTO book);
}
