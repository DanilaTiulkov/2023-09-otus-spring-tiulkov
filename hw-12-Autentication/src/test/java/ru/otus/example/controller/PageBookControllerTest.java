package ru.otus.example.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.example.controllers.PageBookController;
import ru.otus.example.security.SecurityConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PageBookController.class)
@Import(SecurityConfiguration.class)
public class PageBookControllerTest {

    @Autowired
    private MockMvc mvc;



    @Test
    @DisplayName("Успешное получение формы всех книг")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getBooksPage() throws Exception {

        mvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отказ в получении формы всех книг")
    public void getRedirectFromBooksPage() throws Exception {

        mvc.perform(get("/")).andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Успешное получение формы одной книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getBookPage() throws Exception {

        mvc.perform(get("/book/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отказ в получении формы одной книги")
    public void getRedirectFromBookPage() throws Exception {

        mvc.perform(get("/book/1")).andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Успешное получение формы создания книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getNewBookPage() throws Exception {
        mvc.perform(get("/book/new")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отказ в получении формы создания книги")
    public void getErrorNewBookPage() throws Exception {
        mvc.perform(get("/book/new")).andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Успешное получение формы создания книги")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void getUpdateBookPage() throws Exception {
        mvc.perform(get("/book/edit/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отказ в получении формы создания книги")
    public void getRedirectFromUpdateBookPage() throws Exception {
        mvc.perform(get("/book/edit/1")).andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Редирект на bookPage")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void bookPageRedirect() throws Exception {
        mvc.perform(get("/book?bookId=1")).andDo(print())
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Редирект на loginPage")
    public void loginPageRedirect() throws Exception {
        mvc.perform(get("/book?bookId=1")).andDo(print())
                .andExpect(status().is(302));
    }
}
