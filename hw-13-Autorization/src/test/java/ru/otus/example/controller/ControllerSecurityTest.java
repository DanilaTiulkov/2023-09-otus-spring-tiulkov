package ru.otus.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.example.controllers.PageBookController;
import ru.otus.example.models.Book;
import ru.otus.example.rest.AuthorController;
import ru.otus.example.rest.BookController;
import ru.otus.example.rest.GenreController;
import ru.otus.example.security.SecurityConfiguration;
import ru.otus.example.services.AuthorService;
import ru.otus.example.services.BookService;
import ru.otus.example.services.GenreService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PageBookController.class, BookController.class,
        AuthorController.class, GenreController.class})
@Import(SecurityConfiguration.class)
public class ControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @BeforeEach
    void setUp(){
        when(bookService.findById(anyLong())).thenReturn(Optional.of(new Book()));
    }


    @ParameterizedTest
    @DisplayName("Успешный тест доступности для админа")
    @MethodSource("adminFactory")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getFormWithAdmin(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("Успешный тест доступности для юзера")
    @CsvSource({
            "/",
            "/book/1",
            "/api/books",
            "/api/books/1",
            "/api/genres",
            "/api/genres"
    })
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void getFormWithUser(String url) throws Exception {
        mvc.perform(get(url)).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Роль: без аутентификации")
    @MethodSource("factory")
    public void shouldRedirectWithoutRole(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(302));
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Роль: USER")
    @MethodSource("userFactory")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void shouldRedirectWithRoleUser(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(403));
    }

    public static Stream<MockHttpServletRequestBuilder> factory() {
        return Stream.of(get("/"),
                get("/book/1"),
                get("/book/edit/1"),
                get("/book/new"),
                get("/api/books"),
                get("/api/books/1"),
                get("/api/authors"),
                get("/api/genres"),
                post("/api/books"),
                put("/api/books/1"),
                delete("/api/books/1"));
    }

    public static Stream<MockHttpServletRequestBuilder> userFactory() {
        return Stream.of(
                get("/book/edit/1"),
                get("/book/new"),
                post("/api/books"),
                put("/api/books/1"),
                delete("/api/books/1"));
    }

    public static Stream<MockHttpServletRequestBuilder> adminFactory() {
        return Stream.of(get("/"),
                get("/book/1"),
                get("/book/edit/1"),
                get("/book/new"),
                get("/api/books"),
                get("/api/books/1"),
                get("/api/authors"),
                get("/api/genres"));
    }
}
