package ru.otus.example.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.BookDao;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;

import java.util.List;
import java.util.Optional;

@Service("bookService")
public class BookServiceImpl implements BookService {

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
    public Optional<Book> findById(String id) {
        return jpaBookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return jpaBookDao.findAll();
    }

    @Override
    @Transactional
    public Book insert(String title, String authorId, String genreId) {
        return save(null, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Book update(String id, String title, String authorId, String genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        jpaBookDao.deleteById(id);
    }

    public Book save(String id, String title, String authorId, String genreId) {
        var author = jpaAuthorDao.findById(authorId).orElseThrow(() ->
                new EntityNotFoundException("Author doesn't found"));
        var genre = jpaGenreDao.findById(genreId).orElseThrow(() ->
                new EntityNotFoundException("Genre doesn't found"));
        var book = new Book(id, title, author, genre);
        return jpaBookDao.save(book);
    }
}
