package ru.otus.example.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.models.Author;

import java.util.ArrayList;
import java.util.List;

@Service("authorService")
@Slf4j
public class AuthorServiceImpl implements  AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorDao jpaAuthorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao jpaAuthorDao) {
        this.jpaAuthorDao = jpaAuthorDao;
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findAllAuthorsBreaker", fallbackMethod = "findAuthorsFallBack")
    public List<Author> findAll() {
        return jpaAuthorDao.findAll();
    }

    private List<Author> findAuthorsFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new ArrayList<>();
    }
}
