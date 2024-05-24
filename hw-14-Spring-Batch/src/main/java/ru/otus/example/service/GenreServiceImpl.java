package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.GenreDto;
import ru.otus.example.model.mongo.GenreDoc;


@Service
public class GenreServiceImpl implements GenreService {

    @Override
    public GenreDto transform(GenreDoc genreDoc) {
        return new GenreDto(0, genreDoc.getGenreId(), genreDoc.getGenreName());
    }
}
