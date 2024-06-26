package ru.otus.example.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.example.models.Genre;
import ru.otus.example.security.SecurityConfiguration;
import ru.otus.example.services.GenreService;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import(SecurityConfiguration.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    List<Genre> dbGenres;
    private String[] genresTitle;

    @BeforeEach
    public void init() {
        genresTitle = getGenresTitle();
        dbGenres = getDbGenres();
    }

    @Test
    @DisplayName("Поиск всех жанров")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getGenres() throws Exception {
        List<Genre> genres = dbGenres;

        when(genreService.findAll()).thenReturn(genres);

        this.mvc.perform(get("/api/genres")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Fantastic")))
                .andExpect(content().string(containsString("Adventure")))
                .andExpect(content().string(containsString("Horror")));
    }


    public List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, genresTitle[id-1])).toList();
    }
    public String[] getGenresTitle() {
        return new String[]{"Fantastic", "Adventure", "Horror"};
    }

}
