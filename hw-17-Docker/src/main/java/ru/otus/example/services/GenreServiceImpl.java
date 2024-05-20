package ru.otus.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Genre;

import java.util.List;

@Service("genreService")
public class GenreServiceImpl implements GenreService {

    private final GenreDao jpaGenreDao;

    @Autowired
    public GenreServiceImpl(GenreDao jpaGenreDao) {
        this.jpaGenreDao = jpaGenreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return jpaGenreDao.findAll();
    }

}
