package ru.otus.example.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Genre;

import java.util.ArrayList;
import java.util.List;

@Service("genreService")
@Slf4j
public class GenreServiceImpl implements GenreService {

    private static final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreDao jpaGenreDao;

    @Autowired
    public GenreServiceImpl(GenreDao jpaGenreDao) {
        this.jpaGenreDao = jpaGenreDao;
    }

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "findAllGenresBreaker", fallbackMethod = "findGenresFallBack")
    public List<Genre> findAll() {
        return jpaGenreDao.findAll();
    }


    private List<Genre> findGenresFallBack(Throwable ex) {
        log.warn(ex.getMessage());
        return new ArrayList<>();
    }
}
