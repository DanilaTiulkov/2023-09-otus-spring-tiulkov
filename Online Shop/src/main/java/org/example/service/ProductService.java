package org.example.service;

import org.example.model.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findById(long productId);

    List<ProductDto> findAll();

    List<ProductDto> findByTitle(String productTitle);

    List<ProductDto> findByCategoryId(Long categoryId);

    List<ProductDto> findByTitleAndCategoryCategoryId(Long categoryId, String productTitle);

    ProductDto insert(String title, int price, int quantity, long categoryId);

    ProductDto update(long productId, String title, int price, int quantity, long categoryId);

    void delete(long productId);

    void reduceQuantity(int quantity, long productId);


}
