package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.h2.Genre;
import ru.otus.example.model.mongo.GenreDoc;

@Service
public class GenreServiceImpl implements GenreService {


    @Override
    public Genre transform(GenreDoc genreDoc) {
        return new Genre(0, genreDoc.getGenreName());
    }
}
