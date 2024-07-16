package org.example.mapper;

import org.example.model.Category;
import org.example.model.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto getCategoryDto(Category category) {
        long categoryId = category.getCategoryId();
        String categoryName = category.getName();
        return new CategoryDto(categoryId, categoryName);
    }
}
