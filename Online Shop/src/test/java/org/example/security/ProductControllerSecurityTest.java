package org.example.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.ProductNewDto;
import org.example.model.dto.ProductUpdateDto;
import org.example.rest.ProductController;
import org.example.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.sql.Date;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SecurityConfiguration.class)
public class ProductControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;


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
    @DisplayName("Успешный тест доступа. Метод: Get и Delete. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForAdminRoleMethodGetAndDelete(MockHttpServletRequestBuilder builder) throws Exception {
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

    @Test
    @DisplayName("Успешный тест доступа. Метод: Post. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForAdminRoleMethodPost() throws Exception {
        var product = new ProductNewDto("Тестовый продукт", 5000, 1, 1L);
        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступа. Метод: Put. Роль: ADMIN")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldReturnOkForAdminRoleMethodPut() throws Exception {
        var product = new ProductUpdateDto(1L,"Тестовый продукт", 18000, 1,1L);
        mvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("createAndUpdateAndDeleteFactory")
    @DisplayName("Отказ в доступе к методам put, post, delete. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldDeniedForUserRoleMethodPutAndPostAndDelete(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(403));
    }

    @ParameterizedTest
    @MethodSource("createAndUpdateAndDeleteFactory")
    @DisplayName("Отказ в доступе к методам put, post, delete. Роль: USER")
    public void shouldRedirectWithoutRoleMethodPutAndPostAndDelete(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(302));
    }



    public static Stream<MockHttpServletRequestBuilder> factory() {
        return Stream.of(get("/api/products"),
                get("/api/products/1"),
                get("/api/products/categories/1?product-title=Пила"));
    }

    public static Stream<MockHttpServletRequestBuilder> adminFactory() {
        return Stream.of(get("/api/products"),
                get("/api/products/1"),
                get("/api/products/categories/1?product-title=Пила"),
                delete("/api/products/1"));
    }

    public static Stream<MockHttpServletRequestBuilder> createAndUpdateAndDeleteFactory() {
        return Stream.of(post("/api/products"),
                put("/api/products/1"),
                delete("/api/products/1"));
    }

}
