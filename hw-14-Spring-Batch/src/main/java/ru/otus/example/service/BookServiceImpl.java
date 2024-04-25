package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.BookDto;
import ru.otus.example.model.h2.Book;
import ru.otus.example.model.mongo.BookDoc;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public BookDto transform(BookDoc bookDoc, long authorId, long genreId) {
        return new BookDto(0, bookDoc.getTitle(), authorId, genreId);
    }
}
