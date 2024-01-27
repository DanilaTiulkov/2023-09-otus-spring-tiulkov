package ru.otus.example.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.*;
import ru.otus.example.exceptions.EntityNotFoundException;
import ru.otus.example.models.Book;

import java.util.List;
import java.util.Optional;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;


    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional
    public Book insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public Book update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    public Book save(long id, String title, long authorId, long genreId) {
        var author = authorDao.findById(authorId).orElseThrow(() ->
                new EntityNotFoundException("Author doesn't found"));
        var genre = genreDao.findById(genreId).orElseThrow(() ->
                new EntityNotFoundException("Genre doesn't found"));
        var book = new Book(id, title, author, genre);
        return bookDao.save(book);
    }
}
