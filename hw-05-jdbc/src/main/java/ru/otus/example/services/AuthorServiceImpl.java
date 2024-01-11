package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.dao.JdbcAuthorDao;
import ru.otus.example.models.Author;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements  AuthorService {

    private final AuthorDao jdbcAuthorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao jdbcAuthorDao) {
        this.jdbcAuthorDao = jdbcAuthorDao;
    }

    @Override
    public List<Author> findAllAuthors() {
        return jdbcAuthorDao.findAllAuthors();
    }
}
