package org.example.rest;

import jakarta.validation.Valid;
import org.example.model.dto.ProductDto;
import org.example.model.dto.ProductNewDto;
import org.example.model.dto.ProductUpdateDto;
import org.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<ProductDto> findProducts() {
        return productService.findAll();
    }

    @GetMapping("/api/products/{id}")
    public Optional<ProductDto> findProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @GetMapping("/api/products/categories/{categoryId}")
    public List<ProductDto> findProductByIdAndCategory(@PathVariable long categoryId,
                                                       @RequestParam("product-title") String productTitle) {
        if (categoryId == 0) {
            return productService.findByTitle(productTitle);
        } else if (productTitle.isEmpty()) {
            return productService.findByCategoryId(categoryId);
        } else {
            return productService.findByTitleAndCategoryCategoryId(productTitle, categoryId);
        }
    }

    @PostMapping("/api/products")
    public ProductDto createProduct(@Valid @RequestBody ProductNewDto productNewDto) {
        String title = productNewDto.getTitle();
        int price = productNewDto.getPrice();
        int quantity = productNewDto.getQuantity();
        long categoryId = productNewDto.getCategoryId();
        return productService.insert(title, price, quantity, categoryId);
    }

    @PutMapping("/api/products/{id}")
    public ProductDto updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        long productId = productUpdateDto.getProductId();
        String title = productUpdateDto.getTitle();
        int price = productUpdateDto.getPrice();
        int quantity = productUpdateDto.getQuantity();
        long categoryId = productUpdateDto.getCategoryId();
        return productService.update(productId, title, price, quantity, categoryId);
    }


    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.delete(id);
    }
}
