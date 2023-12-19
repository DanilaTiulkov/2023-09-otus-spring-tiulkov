package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.dao.JdbcGenreDao;
import ru.otus.example.models.Genre;

import java.util.List;
import java.util.Optional;

@Service("genreService")
public class GenreServiceImpl implements GenreService {

    private final JdbcGenreDao jdbcGenreDao;

    @Autowired
    public GenreServiceImpl(JdbcGenreDao jdbcGenreDao) {
        this.jdbcGenreDao = jdbcGenreDao;
    }

    @Override
    public List<Genre> findAll() {
        return jdbcGenreDao.findAll();
    }

}
