package org.example.security;


import org.example.controller.PagePersonalController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

@WebMvcTest(PagePersonalController.class)
@Import(SecurityConfiguration.class)
public class PagePersonalControllerSecurityTest {

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
    @MethodSource("factory")
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

    public static Stream<MockHttpServletRequestBuilder> factory() {
        return Stream.of(get("/personal/cart"),
                get("/personal/order/make?total-price=15000"),
                get("/personal/order/1/success"));
    }
}
