package ru.otus.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Genre;


@RestController
public class GenreController {

    private final GenreDao genreDao;

    @Autowired
    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping("/api/genres")
    public Flux<Genre> getGenres() {
        return genreDao.findAll();
    }
}
