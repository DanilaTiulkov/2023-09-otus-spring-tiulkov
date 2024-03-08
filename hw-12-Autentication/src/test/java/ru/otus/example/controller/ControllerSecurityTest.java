package ru.otus.example.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.example.controllers.PageBookController;
import ru.otus.example.security.SecurityConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.params.provider.CsvSource;

@WebMvcTest(PageBookController.class)
@Import(SecurityConfiguration.class)
public class ControllerSecurityTest {

    @Autowired
    private MockMvc mvc;


    @ParameterizedTest
    @DisplayName("Успешное получение форм")
    @CsvSource({
            "/",
            "/book/1",
            "/book/edit/1",
            "/book/new"
    })
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getForm(String url) throws Exception {

        mvc.perform(get(url)).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Запрос по Get")
    @CsvSource({
            "/",
            "/book/1",
            "/book/edit/1",
            "/book/new",
            "/api/books",
            "/api/books/1",
            "/api/authors",
            "/api/genres"
    })
    public void shouldRedirectWithGet(String url) throws Exception {
        mvc.perform(get(url)).andDo(print())
                .andExpect(status().is(302));
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Запрос по Post")
    @CsvSource("/api/books")
    public void shouldRedirectWithPost(String url) throws Exception {
        mvc.perform(post(url)).andDo(print())
                .andExpect(status().is(302));
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Запрос по Put")
    @CsvSource("/api/books/1")
    public void shouldRedirectWithPut(String url) throws Exception {
        mvc.perform(put(url)).andDo(print())
                .andExpect(status().is(302));
    }

    @ParameterizedTest
    @DisplayName("Редирект на login page. Запрос по Delete")
    @CsvSource("/api/books/1")
    public void shouldRedirectWithDelete(String url) throws Exception {
        mvc.perform(delete(url)).andDo(print())
                .andExpect(status().is(302));
    }
}
