package ru.otus.example.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.example.dao.GenreDao;
import ru.otus.example.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@WebFluxTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreDao genreDao;

    Flux<Genre> dbGenres;
    private String[] genresTitle;

    @BeforeEach
    public void init() {
        genresTitle = getGenresTitle();
        dbGenres = getDbGenres();
    }

    @Test
    @DisplayName("Поиск всех жанров")
    public void getGenres() throws Exception {
        when(genreDao.findAll()).thenReturn(dbGenres);
        var expectedSize = 3;
        var expectedGenres = getGenreList().toArray(new Genre[0]);
        webTestClient.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Genre.class)
                .hasSize(expectedSize)
                .contains(expectedGenres);
    }


    public Flux<Genre> getDbGenres() {
        return Flux.fromIterable(getGenreList());
    }

    public List<Genre> getGenreList() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, genresTitle[id-1])).toList();
    }
    public String[] getGenresTitle() {
        return new String[]{"Fantastic", "Adventure", "Horror"};
    }

}
