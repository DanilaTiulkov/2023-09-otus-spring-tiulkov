package org.example.service;

import org.example.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    public List<CategoryDto> findCategories();
}
