package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.AppUserService;
import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.repositories.AuthorRepository;
import com.bujo.bookshelf.book.repositories.BookRepository;
import com.bujo.bookshelf.book.validators.BookValidation;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * BookServiceImpl is a service class that provides CRUD operations for {@link Book} objects.
 *
 * @author skylar
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AppUserService appUserService;
    private final BookValidation validation;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, AppUserService appUserService, BookValidation validation) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.appUserService = appUserService;
        this.validation = validation;
    }

    @Override
    public Set<BookDTO> findInProgress(Long appUserId) {
        AppUser userResult = appUserService.findById(appUserId).orElse(null);
        if (userResult == null) {
            return null;
        }

        Set<Book> allBooks = findByUser(userResult);
        Set<Book> inProgressBooks = allBooks.stream()
                .filter(Book::isInProgress)
                .collect(Collectors.toSet());
        return inProgressBooks.stream()
                .map(book -> new BookDTO(
                        book.getBookId(),
                        book.getUser().getAppUserId(),
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getLanguage(),
                        book.getPages()))
                .collect(Collectors.toSet());
    }

    @Override
    public Result<BookDTO> create(BookDTO bookDto) {
        Result<BookDTO> result = validation.validate(bookDto);

        if (!result.isSuccess()) {
            return result;
        }
        Book newBook = new Book();
        Author author = authorRepository.findByName(bookDto.author());

        if (!isPresent(author)) {
            author = authorRepository.save(createAuthor(bookDto.author()));
        }
        AppUser appUser = appUserService.findById(bookDto.appUserId()).orElse(null);

        if (!isPresent(appUser)) {
            result.addMessage(ActionStatus.INVALID, "invalid app user");
            return result;
        }

        newBook.setAuthor(author);
        newBook.setUser(appUser);
        newBook.setTitle(bookDto.title());
        newBook.setLanguage(bookDto.language());
        newBook.setPages(bookDto.pages());

        newBook = bookRepository.save(newBook);
        result.setPayload(new BookDTO(
                newBook.getBookId(),
                newBook.getUser().getAppUserId(),
                newBook.getTitle(),
                newBook.getAuthor().getName(),
                newBook.getLanguage(),
                newBook.getPages()));

        return result;
    }

    @Override
    public Set<Book> findByUser(AppUser appUser) {
        if (appUserService.findById(appUser.getAppUserId()).isEmpty()) {
            return null;
        }
        return bookRepository.findByUser(appUser);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    private boolean isPresent(Object object) {
        return object != null;
    }

    private Author createAuthor(String name) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        return newAuthor;
    }

    @Override
    public void deleteById(Long id, Long appUserId) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null || !book.getUser().getAppUserId().equals(appUserId)) {
            return;
        }

        Author author = book.getAuthor();
        bookRepository.deleteById(id);
        if (author.getBooks().size() == 1) {
            authorRepository.deleteById(author.getAuthorId());
        }
    }

    @Override
    public Result<BookDTO> update(BookDTO book) {
        Result<BookDTO> result = new Result<>();
        Book currentBook = bookRepository.findById(book.bookId()).orElse(null);

        if (currentBook == null || !currentBook.getUser().getAppUserId().equals(book.appUserId())) {
            result.addMessage(ActionStatus.NOT_FOUND, "book was not found");
            return result;
        }

        Author currentAuthor = authorRepository
                .findById(currentBook.getAuthor().getAuthorId()).orElse(null);

        result = validation.validate(book);

        if (!result.isSuccess()) {
            return result;
        }

        if (isPresent(currentAuthor) && !currentAuthor.getName().equals(book.author())) {
            updateBookAuthor(currentBook, currentAuthor, book.author());
        }

        currentBook.setTitle(book.title());
        currentBook.setLanguage(book.language());
        currentBook.setPages(book.pages());

        bookRepository.saveAndFlush(currentBook);
        return result;
    }

    private void updateBookAuthor(Book book, Author author, String name) {
        if (isPresent( findAuthorByName(name) )) {
            book.setAuthor(findAuthorByName(name));
            deleteAuthorIfOnlyOneBook(author);
        } else {
            if (author.getBooks().size() == 1) {
                author.setName(name);
                saveAuthor(author);
            } else {
                book.setAuthor(updateNewAuthor(name));
                deleteAuthorIfOnlyOneBook(author);
            }
        }
    }

    private Author findAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    private void deleteAuthorIfOnlyOneBook(Author author) {
        if (isPresent(author) && author.getBooks().size() == 1) {
            authorRepository.deleteById(author.getAuthorId());
        }
    }

    private Author updateNewAuthor(String name) {
        return saveAuthor(createAuthor(name));
    }

    private Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
}
