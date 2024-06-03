package org.example.mapper;

import org.example.model.Product;
import org.example.model.dto.CategoryDto;
import org.example.model.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductDto getProductDto(Product product) {
        long productId = product.getProductId();
        String title = product.getTitle();
        int price = product.getPrice();
        int quantity = product.getQuantity();
        CategoryDto category = categoryMapper.getCategoryDto(product.getCategory());
        return new ProductDto(productId, title, price, quantity, category);
    }
}
