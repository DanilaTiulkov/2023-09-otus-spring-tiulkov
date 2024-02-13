package ru.otus.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.example.models.Author;

public interface AuthorDao extends MongoRepository<Author, String> {
}
