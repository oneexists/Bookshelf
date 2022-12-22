package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.AppUserRepository;
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

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AppUserRepository appUserRepository;
    private final BookValidation validation;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, AppUserRepository appUserRepository, BookValidation validation) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.appUserRepository = appUserRepository;
        this.validation = validation;
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
        AppUser appUser = appUserRepository.findById(bookDto.appUserId()).orElse(null);

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
