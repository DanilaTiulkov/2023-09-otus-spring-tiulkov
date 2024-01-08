package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.dao.JdbcAuthorDao;
import ru.otus.example.models.Author;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements  AuthorService {

    private final JdbcAuthorDao jdbcAuthorDao;

    @Autowired
    public AuthorServiceImpl(JdbcAuthorDao jdbcAuthorDao) {
        this.jdbcAuthorDao = jdbcAuthorDao;
    }

    @Override
    public List<Author> findAllAuthors() {
        return jdbcAuthorDao.findAllAuthors();
    }
}
