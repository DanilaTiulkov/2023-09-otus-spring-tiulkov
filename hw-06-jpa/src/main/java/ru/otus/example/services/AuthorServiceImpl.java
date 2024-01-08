package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.JpaAuthorDao;
import ru.otus.example.models.Author;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements  AuthorService {

    private final JpaAuthorDao jpaAuthorDao;

    @Autowired
    public AuthorServiceImpl(JpaAuthorDao jpaAuthorDao) {
        this.jpaAuthorDao = jpaAuthorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAllAuthors() {
        return jpaAuthorDao.findAllAuthors();
    }
}
