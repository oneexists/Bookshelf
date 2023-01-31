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
 * BookServiceImpl is a service class that provides CRUD operations for {@link Book} objects
 * and collections of books for specified {@link AppUser}.
 *
 * @author skylar
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final String INVALID_APP_USER_ERR = "invalid app user";
    private final String BOOK_NOT_FOUND_ERR = "book was not found";
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
    public Result<BookDTO> create(BookDTO bookDto) {
        Result<BookDTO> result = validation.validate(bookDto);

        if (!result.isSuccess()) {
            return result;
        }
        Author author = createAuthorFromDto(bookDto);
        AppUser appUser = findAppUserById(bookDto.appUserId());

        if (!isPresent(appUser)) {
            result.addMessage(ActionStatus.INVALID, INVALID_APP_USER_ERR);
            return result;
        }

        Book newBook = createBook(bookDto, author, appUser);
        result.setPayload(
                BookDTO.fromBook(bookRepository.save(newBook))
        );
        return result;
    }

    private Author createAuthorFromDto(BookDTO bookDto) {
        Author author = authorRepository.findByName(bookDto.author());

        if (!isPresent(author)) {
            author = authorRepository.save(createAuthor(bookDto.author()));
        }
        return author;
    }

    private Book createBook(BookDTO dto, Author author, AppUser user) {
        Book newBook = new Book();
        if (isPresent(user)) {
            newBook.setUser(user);

            if (isPresent(dto)) {
                newBook.setTitle(dto.title());
                newBook.setLanguage(dto.language());
                newBook.setPages(dto.pages());
            }
            if (isPresent(author)) {
                newBook.setAuthor(author);
            }
        }
        return newBook;
    }

    @Override
    public Set<BookDTO> findInProgress(Long appUserId) {
        AppUser userResult = findAppUserById(appUserId);
        if (!isPresent(userResult)) {
            return null;
        }

        Set<Book> allBooks = findByUser(userResult);
        Set<Book> inProgressBooks = allBooks.stream()
                .filter(Book::isInProgress)
                .collect(Collectors.toSet());

        return inProgressBooks.stream()
                .map(BookDTO::fromBook)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<BookDTO> findRead(Long appUserId) {
        AppUser userResult = findAppUserById(appUserId);
        if (!isPresent(userResult)) {
            return null;
        }

        Set<Book> allBooks = findByUser(userResult);
        Set<Book> readBooks = allBooks.stream()
                .filter(book -> book.getReadingLogs().stream()
                        .filter(readingLog -> readingLog.getFinish() != null).collect(Collectors.toSet()).size() > 0)
                .collect(Collectors.toSet());
        return readBooks.stream()
                .map(BookDTO::fromBook)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<BookDTO> findUnread(Long appUserId) {
        AppUser userResult = findAppUserById(appUserId);
        if (!isPresent(userResult)) {
            return null;
        }

        Set<Book> allBooks = findByUser(userResult);
        Set<Book> unreadBooks = allBooks.stream()
                .filter(book -> book.getReadingLogs().size() == 0)
                .collect(Collectors.toSet());
        return unreadBooks.stream()
                .map(BookDTO::fromBook)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Book> findByUser(AppUser appUser) {
        if (isPresent(appUser) && isPresent(findAppUserById(appUser.getAppUserId()))) {
            return bookRepository.findByUser(appUser);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void deleteById(Long id, Long appUserId) {
        Book book = findById(id).orElse(null);
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
        Book currentBook = findById(book.bookId()).orElse(null);

        if (isValidBook(currentBook, book.appUserId())) {
            result.addMessage(ActionStatus.NOT_FOUND, BOOK_NOT_FOUND_ERR);
            return result;
        }

        Author currentAuthor = findAuthorById(currentBook.getAuthor().getAuthorId());

        result = validation.validate(book);

        if (!result.isSuccess()) {
            return result;
        }

        if (isPresent(currentAuthor) && !currentAuthor.getName().equals(book.author())) {
            Author newAuthor = updateAuthor(currentAuthor, book.author());
            currentBook.setAuthor(newAuthor);
        }

        currentBook.setTitle(book.title());
        currentBook.setLanguage(book.language());
        currentBook.setPages(book.pages());

        bookRepository.saveAndFlush(currentBook);
        return result;
    }

    private boolean isValidBook(Book currentBook, Long appUserId) {
        return currentBook == null || !currentBook.getUser().getAppUserId().equals(appUserId);
    }

    private Author updateAuthor(Author author, String name) {
        Author updatedAuthor = authorRepository.findByName(name);
        if (!isPresent(updatedAuthor) && author.getBooks().size() == 1) {
            updatedAuthor = updateAuthorName(author, name);
        } else {
            if (!isPresent(updatedAuthor)) {
                updatedAuthor = authorRepository.save(createAuthor(name));
            }
            deleteAuthorIfOnlyOneBook(author);
        }
        return updatedAuthor;
    }

    private Author updateAuthorName(Author author, String name) {
        author.setName(name);
        authorRepository.save(author);
        return author;
    }

    private void deleteAuthorIfOnlyOneBook(Author author) {
        if (isPresent(author) && author.getBooks().size() == 1) {
            authorRepository.deleteById(author.getAuthorId());
        }
    }

    private Author createAuthor(String name) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        return newAuthor;
    }

    private AppUser findAppUserById(Long appUserId) {
        return appUserService.findById(appUserId).orElse(null);
    }

    private Author findAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    private boolean isPresent(Object object) {
        return object != null;
    }
}
