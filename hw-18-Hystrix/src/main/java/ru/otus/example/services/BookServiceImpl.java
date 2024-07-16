package ru.otus.example.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("bookService")
@Slf4j
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookDao jpaBookDao;

    private final AuthorDao jpaAuthorDao;

    private final GenreDao jpaGenreDao;


    public BookServiceImpl(BookDao jpaBookDao, AuthorDao jpaAuthorDao, GenreDao jpaGenreDao) {
        this.jpaBookDao = jpaBookDao;
        this.jpaAuthorDao = jpaAuthorDao;
        this.jpaGenreDao = jpaGenreDao;
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findBookBreaker", fallbackMethod = "findBookFallBack")
    public Optional<Book> findById(long id) {
        return jpaBookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findAllBooksBreaker", fallbackMethod = "findBooksFallBack")
    public List<Book> findAll() {
        return jpaBookDao.findAll();
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "insertBookBreaker", fallbackMethod = "insertBookFallBack")
    public Book insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "updateBookBreaker", fallbackMethod = "updateBookFallBack")
    public Book update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "deleteBookBreaker", fallbackMethod = "deleteBookFallBack")
    public void deleteById(long id) {
        jpaBookDao.deleteById(id);
    }

    public Book save(long id, String title, long authorId, long genreId) {
        var author = jpaAuthorDao.findById(authorId).orElseThrow(() ->
                new EntityNotFoundException("Author doesn't found"));
        var genre = jpaGenreDao.findById(genreId).orElseThrow(() ->
                new EntityNotFoundException("Genre doesn't found"));
        var book = new Book(id, title, author, genre);
        return jpaBookDao.save(book);
    }

    private List<Book> findBooksFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new ArrayList<>();
    }

    private Optional<Book> findBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        var book = new Book(0, null, null, null);
        return Optional.of(book);
    }

    private Book insertBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new Book(0, null, null, null);
    }

    private Book updateBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new Book(0, null, null, null);
    }

    private void deleteBookFallBack(Throwable ex) {
        log.warn(ex.getMessage());
    }
}
