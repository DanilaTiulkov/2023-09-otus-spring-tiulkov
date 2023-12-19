package ru.otus.example.services;

import ru.otus.example.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> findAll();

}
