package ru.otus.example.service;

import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.mongo.AuthorDoc;


@Service
public class AuthorServiceImpl implements AuthorService {

    @Override
    public AuthorDto transform(AuthorDoc authorDoc) {
        return new AuthorDto(0, authorDoc.getAuthorId(), authorDoc.getFullName());
    }
}
