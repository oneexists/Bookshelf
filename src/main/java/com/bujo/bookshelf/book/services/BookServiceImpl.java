package com.bujo.bookshelf.book.services;

import com.bujo.bookshelf.appUser.AppUserRepository;
import com.bujo.bookshelf.appUser.models.AppUser;
import com.bujo.bookshelf.book.models.Author;
import com.bujo.bookshelf.book.models.Book;
import com.bujo.bookshelf.book.models.BookDTO;
import com.bujo.bookshelf.book.repositories.AuthorRepository;
import com.bujo.bookshelf.book.repositories.BookRepository;
import com.bujo.bookshelf.response.ActionStatus;
import com.bujo.bookshelf.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (author == null) {
            author = createAuthor(bookDto.author());
        }
        AppUser appUser = appUserRepository.findById(bookDto.appUserId()).orElse(null);

        if (appUser == null) {
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

        if (authorRepository.findByName(book.author()) != null) {
            updateExistingAuthor(currentBook, book.author());
            deleteAuthorIfOnlyOneBook(currentAuthor);
        } else {
            if (isPresent(currentAuthor) && currentAuthor.getBooks().size() == 1) {
                updateAuthorName(currentAuthor, book.author());
            } else {
                updateNewAuthor(currentBook, book.author());
                deleteAuthorIfOnlyOneBook(currentAuthor);
            }
        }

        currentBook.setTitle(book.title());
        currentBook.setLanguage(book.language());
        currentBook.setPages(book.pages());

        bookRepository.saveAndFlush(currentBook);
        return result;
    }

    private boolean isPresent(Author author) {
        return author != null;
    }

    private void deleteAuthorIfOnlyOneBook(Author author) {
        if (isPresent(author) && author.getBooks().size() == 1) {
            authorRepository.deleteById(author.getAuthorId());
        }
    }

    private void updateNewAuthor(Book book, String name) {
        Author newAuthor = authorRepository.save(createAuthor(name));
        book.setAuthor(newAuthor);
    }

    private Author createAuthor(String name) {
        Author newAuthor = new Author();
        newAuthor.setName(name);
        return newAuthor;
    }

    private void updateExistingAuthor(Book book, String name) {
        book.setAuthor(authorRepository.findByName(name));
    }

    private void updateAuthorName(Author author, String name) {
        author.setName(name);
        authorRepository.save(author);
    }
}
