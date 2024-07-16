package org.example.service;

import org.example.mapper.CategoryMapper;
import org.example.model.Category;
import org.example.model.dto.CategoryDto;
import org.example.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    private List<Category> dbCategories;

    @BeforeEach
    public void init() {
        dbCategories = getCategories();
    }

    @Test
    @DisplayName("Успешное получение всех категорий")
    public void shouldReturnCategoriesList() {
        Mockito.when(categoryRepository.findAll()).thenReturn(dbCategories);
        var expectingCategories = getCategoriesDto();
        var actualCategories = categoryService.findCategories();
        assertThat(actualCategories).containsExactlyElementsOf(expectingCategories);
    }

    public List<Category> getCategories() {
        return List.of(new Category(1, "Дрели"),
                new Category(2, "Пилы"),
                new Category(3, "Компрессоры"));
    }

    public List<CategoryDto> getCategoriesDto() {
        return List.of(new CategoryDto(1, "Дрели"),
                new CategoryDto(2, "Пилы"),
                new CategoryDto(3, "Компрессоры"));
    }
}
