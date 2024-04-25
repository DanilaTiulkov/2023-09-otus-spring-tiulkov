package ru.otus.example.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.example.models.Book;


public interface BookDao extends ReactiveCrudRepository<Book, Long> {

}
