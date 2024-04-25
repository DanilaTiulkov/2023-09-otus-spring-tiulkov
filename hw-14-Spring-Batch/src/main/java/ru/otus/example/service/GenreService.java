package ru.otus.example.service;

import ru.otus.example.model.h2.Genre;
import ru.otus.example.model.mongo.GenreDoc;

public interface GenreService {

    public Genre transform(GenreDoc genreDoc);
}
