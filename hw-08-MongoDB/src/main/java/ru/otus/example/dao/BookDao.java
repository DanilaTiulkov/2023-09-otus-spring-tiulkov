package ru.otus.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.example.models.Book;

public interface BookDao extends MongoRepository<Book, String> {
}
