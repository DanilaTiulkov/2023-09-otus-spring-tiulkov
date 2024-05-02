package ru.otus.example.repository;

import ru.otus.example.model.h2.temp.GenreTemp;

import java.util.List;

public interface GenreTempRepository {

    List<GenreTemp> findAll();
}
