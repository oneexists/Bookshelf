package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.response.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookService {
    /**
     * Creates a new {@link Book}.
     *
     * @param bookDto the DTO containing the information for the new {@link Book}
     * @return the result of the create operation, containing the newly created {@link Book} if successful
     */
    Result<BookDTO> create(BookDTO bookDto);

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
