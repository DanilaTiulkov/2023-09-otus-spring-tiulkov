package ru.otus.example.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.example.dao.AuthorDao;
import ru.otus.example.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@WebFluxTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorDao authorDao;

    private Flux<Author> dbAuthors;

    private String[] authorsName;

    @BeforeEach
    public void init() {
        authorsName = getAuthorsName();
        dbAuthors = getDbAuthors();
    }


    @Test
    @DisplayName("Поиск всех авторов")
    public void getAuthors() {
        when(authorDao.findAll()).thenReturn(dbAuthors);
        var expectedAuthors = getAuthorsList().toArray(Author[]::new);
        var expectedSize = 3;
        webTestClient.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Author.class)
                .hasSize(expectedSize)
                .contains(expectedAuthors);
    }


    private Flux<Author> getDbAuthors() {
        return Flux.fromIterable(getAuthorsList());
    }

    private List<Author> getAuthorsList() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, authorsName[id - 1]))
                .toList();
    }

    private String[] getAuthorsName() {
        return new String[]{"Ivan Sergeevich", "Ilya Abramov", "Mikhail Andreevich"};
    }
}
