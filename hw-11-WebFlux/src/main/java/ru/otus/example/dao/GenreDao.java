package ru.otus.example.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.example.models.Genre;

public interface GenreDao extends ReactiveCrudRepository<Genre, Long> {
}
