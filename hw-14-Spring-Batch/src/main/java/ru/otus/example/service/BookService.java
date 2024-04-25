package ru.otus.example.service;

import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.mongo.BookDoc;

public interface BookService {

    public BookDto transform(BookDoc bookDoc, long authorId, long genreId);
}
