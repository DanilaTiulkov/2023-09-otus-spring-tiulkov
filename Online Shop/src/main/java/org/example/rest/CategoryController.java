package org.example.rest;

import org.example.model.dto.CategoryDto;
import org.example.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories")
    public List<CategoryDto> findCategories() {
        return categoryService.findCategories();
    }
}
