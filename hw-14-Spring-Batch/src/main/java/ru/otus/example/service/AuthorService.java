package ru.otus.example.service;

import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.h2.Author;
import ru.otus.example.model.mongo.AuthorDoc;

public interface AuthorService {

    AuthorDto transform(AuthorDoc authorDoc);
}
