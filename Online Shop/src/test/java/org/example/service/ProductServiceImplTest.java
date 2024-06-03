package org.example.service;

import org.example.model.Category;
import org.example.model.Product;
import org.example.model.dto.CategoryDto;
import org.example.model.dto.ProductDto;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @MockBean
    ProductRepository productRepository;

    private List<Product> dbProducts;


    @BeforeEach
    public void init() {
        dbProducts = getProducts();
    }


    @Test
    @DisplayName("Получение всех продуктов")
    public void shouldReturnProductsList() {
        Mockito.when(productRepository.findAll()).thenReturn(dbProducts);

        var actualProducts = productService.findAll();
        var expectedProducts = getProductsDto();
        assertThat(actualProducts).containsExactlyElementsOf(expectedProducts);

    }

    public List<Product> getProducts() {
        return List.of(new Product(1L, "Bosch GSR 180-Li Professional", 18000, 2, new Category(1, "Дрели")),
                new Product(2L, "Дисковая пила Makita HS301DZ", 7790, 5, new Category(2, "Пилы")),
                new Product(3L, "Воздуходувка портативная беспроводная аккумуляторная", 2184, 6, new Category(3, "Компрессоры")));
    }

    public List<ProductDto> getProductsDto() {
        return List.of(new ProductDto(1L, "Bosch GSR 180-Li Professional", 18000, 2, new CategoryDto(1, "Дрели")),
                new ProductDto(2L, "Дисковая пила Makita HS301DZ", 7790, 5, new CategoryDto(2, "Пилы")),
                new ProductDto(3L, "Воздуходувка портативная беспроводная аккумуляторная", 2184, 6, new CategoryDto(3, "Компрессоры")));
    }


}
