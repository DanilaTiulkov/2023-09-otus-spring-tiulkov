package org.example.security;


import org.example.controller.PageProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PageProductController.class)
@Import(SecurityConfiguration.class)
public class PageProductControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @MethodSource("factory")
    @DisplayName("Успешный тест доступа. Метод: Get. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldReturnOkForUserRoleMethodGet(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("adminFactory")
    @DisplayName("Успешный тест доступа. Метод: Get. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForAdminRoleMethodGet(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("factory")
    @DisplayName("Успешный тест доступа. Метод: Get. Роль: без роли")
    public void shouldReturnOkWithoutRoleMethodGet(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("createFactory")
    @DisplayName("Отказ в доступе. Метод: Get. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldReturnDeniedForUserRoleMethodGet(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(403));
    }

    @ParameterizedTest
    @MethodSource("createFactory")
    @DisplayName("Редирект. Метод: Get. Роль: без роли")
    public void shouldReturnRedirectWithoutRoleMethodGet(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(302));
    }



    public static Stream<MockHttpServletRequestBuilder> factory() {
        return Stream.of(get("/"),
                get("/products"),
                get("/products/1"),
                get("/products/categories/1?product-title=Пила"));
    }

    public static Stream<MockHttpServletRequestBuilder> adminFactory() {
        return Stream.of(get("/"),
                get("/products"),
                get("/products/1"),
                get("/products/categories/1?product-title=Пила"),
                get("/products/new"),
                get("/products/edit/1"));
    }

    public static Stream<MockHttpServletRequestBuilder> createFactory() {
        return Stream.of(get("/products/new"),
                get("/products/edit/1"));
    }
}
