package ru.otus.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.example.models.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {
}
