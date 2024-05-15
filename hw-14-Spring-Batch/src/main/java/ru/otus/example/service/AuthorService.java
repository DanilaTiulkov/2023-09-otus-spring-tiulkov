package ru.otus.example.service;

import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.mongo.AuthorDoc;

import java.util.Map;

public interface AuthorService {

    AuthorDto transform(AuthorDoc authorDoc);

    Map<String, Long> findAllAuthorsIds();
}
