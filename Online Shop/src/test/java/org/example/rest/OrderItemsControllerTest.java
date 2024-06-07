package org.example.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.OrderItemsNewDto;
import org.example.service.OrderItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderItemsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemsService orderItemsService;


    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }


    @Test
    @DisplayName("Создание деталей заказа")
    public void shouldSaveOrderItems() throws Exception {
        var savedOrderItems = new OrderItemsNewDto(1L, 1L, 1, 18000);

        mvc.perform(post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedOrderItems)))
                .andExpect(status().isOk());
    }
}
