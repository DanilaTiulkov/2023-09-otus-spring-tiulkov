package org.example.rest;

import org.example.model.dto.CategoryDto;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CategoryService categoryService;

    private List<CategoryDto> categoriesDto;

    @BeforeEach
    public void inti() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
        categoriesDto = getCategoriesDto();
    }


    @Test
    @DisplayName("Получение всех категорий")
    public void shouldReturnCategoriesList() throws Exception {
        Mockito.when(categoryService.findCategories()).thenReturn(categoriesDto);

        mvc.perform(get("/api/categories")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Дрели")))
                .andExpect(content().string(containsString("Пилы")))
                .andExpect(content().string(containsString("Компрессоры")));
    }

    public List<CategoryDto> getCategoriesDto() {
        return List.of(new CategoryDto(1, "Дрели"),
                new CategoryDto(2, "Пилы"),
                new CategoryDto(3, "Компрессоры"));
    }
}
