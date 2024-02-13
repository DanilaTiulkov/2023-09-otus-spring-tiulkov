package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.models.Author;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements  AuthorService {

    private final AuthorDao jpaAuthorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao jpaAuthorDao) {
        this.jpaAuthorDao = jpaAuthorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAllAuthors() {
        return jpaAuthorDao.findAll();
    }
}
