package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Genre;

import java.util.List;

@Service("genreService")
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao jdbcGenreDao) {
        this.genreDao = jdbcGenreDao;
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

}
