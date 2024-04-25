package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.h2.Author;
import ru.otus.example.model.mongo.AuthorDoc;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Override
    public Author transform(AuthorDoc authorDoc) {
        return new Author(0, authorDoc.getFullName());
    }
}
