package org.example.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.OrderItemsNewDto;
import org.example.rest.OrderItemsController;
import org.example.service.OrderItemsService;
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

@WebMvcTest(OrderItemsController.class)
@Import(SecurityConfiguration.class)
public class OrderItemsControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderItemsService orderItemsService;


    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldReturnOkForUserRoleMethodPost() throws Exception {
        var orderItems = new OrderItemsNewDto(1L, 1L, 1, 18000);
        mvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItems)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForUAdminRoleMethodPost() throws Exception {
        var orderItems = new OrderItemsNewDto(1L, 1L, 1, 18000);
        mvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItems)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: без роли")
    public void shouldReturnOkWithoutRoleMethodPost() throws Exception {
        var orderItems = new OrderItemsNewDto(1L, 1L, 1, 18000);
        mvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderItems)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
