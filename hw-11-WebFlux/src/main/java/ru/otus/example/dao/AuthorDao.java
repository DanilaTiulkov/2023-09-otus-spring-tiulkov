package ru.otus.example.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.example.models.Author;


public interface AuthorDao extends ReactiveCrudRepository<Author, Long> {
}
