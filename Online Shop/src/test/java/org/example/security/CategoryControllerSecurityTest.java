package org.example.security;


import org.example.model.dto.CategoryDto;
import org.example.rest.CategoryController;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(CategoryController.class)
@Import(SecurityConfiguration.class)
public class CategoryControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
     private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        Mockito.when(categoryService.findCategories()).thenReturn(getCategoriesDto());
    }

    @Test
    @DisplayName("Успешный тест доступности для админа")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldReturnOkForAdminRole() throws Exception {
        mvc.perform(get("/api/categories")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступности для юзера")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    public void shouldReturnOkForUserRole() throws Exception {
        mvc.perform(get("/api/categories")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест доступности без роли")
    public void shouldReturnOkWithoutRole() throws Exception {
        mvc.perform(get("/api/categories")).andDo(print())
                .andExpect(status().isOk());
    }



    public List<CategoryDto> getCategoriesDto() {
        return List.of(new CategoryDto(1, "Дрели"),
                new CategoryDto(2, "Пилы"),
                new CategoryDto(3, "Компрессоры"));
    }
}
