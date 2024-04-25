package ru.otus.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.models.Author;


@RestController
public class AuthorController {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorController(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @GetMapping("/api/authors")
    public Flux<Author> getAuthors() {
        return authorDao.findAll();
    }
}
