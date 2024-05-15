package ru.otus.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.example.model.dto.AuthorDto;
import ru.otus.example.model.mongo.AuthorDoc;

import java.util.Map;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final Map<String, Long> authorsIds;

    @Autowired
    public AuthorServiceImpl(Map<String, Long> authorsIds) {
        this.authorsIds = authorsIds;
    }

    @Override
    public AuthorDto transform(AuthorDoc authorDoc) {
        return new AuthorDto(0, authorDoc.getAuthorId(), authorDoc.getFullName());
    }

    @Override
    public Map<String, Long> findAllAuthorsIds() {
        return authorsIds;
    }
}
