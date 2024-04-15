package ru.otus.example.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.example.models.Book;

public interface BookDaoCustom {

    public Flux<Book> findAll();

    public Mono<Book> findById(long id);

    public Mono<Book> save(Book book);

    public Mono<Void> deleteById(long id);
}
