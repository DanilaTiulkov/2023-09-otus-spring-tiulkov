package ru.otus.example.service;

import ru.otus.example.model.dto.GenreDto;
import ru.otus.example.model.mongo.GenreDoc;


public interface GenreService {

    GenreDto transform(GenreDoc genreDoc);

}
