package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.models.Author;

import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAllAuthors() {
        List<Author> authorList = authorDao.findAll();
        return authorDao.findAll();
    }
}
