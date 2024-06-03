package org.example.service;

import org.example.mapper.CategoryMapper;
import org.example.model.Category;
import org.example.model.dto.CategoryDto;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> findCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category ->
                new CategoryDto(category.getCategoryId(), category.getName())).toList();
    }
}
