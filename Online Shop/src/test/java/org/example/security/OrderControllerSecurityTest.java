package org.example.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.OrderNewDto;
import org.example.rest.OrderController;
import org.example.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

@WebMvcTest(OrderController.class)
@Import(SecurityConfiguration.class)
public class OrderControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldReturnOkForUserRoleMethodPost() throws Exception {
        var order = new OrderNewDto("Петр", "+7(999)999-99-99", "testEmail@mail.ru", "Тестовый адрес", new String[]{"1=1"}, 15000,  new Date(2024, 4, 2));
        mvc.perform(post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForAdminRoleMethodPost() throws Exception {
        var order = new OrderNewDto("Петр", "+7(999)999-99-99", "testEmail@mail.ru", "Тестовый адрес", new String[]{"1=1"}, 15000,  new Date(2024, 4, 2));
        mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: без роли")
    public void shouldReturnOkWithoutRoleMethodPost() throws Exception {
        var order = new OrderNewDto("Петр", "+7(999)999-99-99", "testEmail@mail.ru", "Тестовый адрес", new String[]{"1=1"}, 15000,  new Date(2024, 4, 2));
        mvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
