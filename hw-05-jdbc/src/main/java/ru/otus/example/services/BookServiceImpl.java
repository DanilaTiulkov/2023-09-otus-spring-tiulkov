package ru.otus.example.services;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.example.dao.JdbcAuthorDao;
import ru.otus.example.dao.JdbcBookDao;
import ru.otus.example.dao.JdbcGenreDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;

import java.util.List;
import java.util.Optional;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private final JdbcBookDao jdbcBookDao;

    private final JdbcAuthorDao jdbcAuthorDao;

    private final JdbcGenreDao jdbcGenreDao;


    public BookServiceImpl(JdbcBookDao jdbcBookDao, JdbcAuthorDao jdbcAuthorDao, JdbcGenreDao jdbcGenreDao) {
        this.jdbcBookDao = jdbcBookDao;
        this.jdbcAuthorDao = jdbcAuthorDao;
        this.jdbcGenreDao = jdbcGenreDao;
    }

    @Override
    public Optional<Book> findById(long id) {
        return jdbcBookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcBookDao.findAll();
    }

    @Override
    public Book insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    public Book update(long id, String title, long authorId, long genreId) {
        var book = jdbcBookDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Book doesn't found"));
        return save(id, title, authorId, genreId);
    }

    @Override
    public void delete(long id) {
        jdbcBookDao.deleteById(id);
    }

    public Book save(long id, String title, long authorId, long genreId) {
        var author = jdbcAuthorDao.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author doesn't found"));
        var genre = jdbcGenreDao.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre doesn't found"));
        var book = new Book(id, title, author, genre);
        return jdbcBookDao.save(book);
    }
}
