package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mapper.ProductMapper;
import org.example.model.Product;
import org.example.model.dto.ProductDto;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    @Autowired
    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> findById(long productId) {
        var product = productRepository.findById(productId);
        return product.map(productMapper::getProductDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        var products = productRepository.findAll();
        return products.stream().map(productMapper::getProductDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByTitle(String productTitle) {
        var products = productRepository.findByTitle(productTitle);
        return products.stream().map(productMapper::getProductDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByCategoryId(Long categoryId) {
        var products = productRepository.findByCategoryCategoryId(categoryId);
        return products.stream().map(productMapper::getProductDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findByTitleAndCategoryCategoryId(Long categoryId, String productTitle) {
        var products = productRepository.findByTitleAndCategoryCategoryId(categoryId, productTitle);
        return products.stream().map(productMapper::getProductDto).toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto insert(String title, int price, int quantity, long categoryId) {
        var product = save(0, title, price, quantity, categoryId);
        return productMapper.getProductDto(product);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto update(long productId, String title, int price, int quantity, long categoryId) {
        var product = save(productId, title, price, quantity, categoryId);
        return productMapper.getProductDto(product);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public void reduceQuantity(int quantity, long productId) {
        productRepository.reduceQuantity(quantity, productId);
    }


    private Product save(long productId, String title, int price, int quantity, long categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        var product = new Product(productId, title, price, quantity, category);
        return productRepository.save(product);
    }
}
