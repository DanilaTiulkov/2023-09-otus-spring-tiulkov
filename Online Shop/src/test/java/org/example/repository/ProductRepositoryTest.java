package org.example.repository;

import org.example.model.Category;
import org.example.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Получение продукта по id")
    public void shouldReturnProductById() {
        var expectedProduct = em.find(Product.class, 1L);
        var actualProduct = productRepository.findById(1L);
        assertThat(actualProduct)
                .isPresent()
                .get()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("Получение продуктов по названию")
    public void shouldReturnProductByTitle() {
        var expectedProducts = List.of(em.find(Product.class, 1L));
        var actualProducts = productRepository.findByTitle("Bosch GSR 180-Li Professional");
        assertThat(actualProducts).containsExactlyElementsOf(expectedProducts);
    }

    @Test
    @DisplayName("Получение продуктов по id категории")
    public void shouldReturnProductByCategoryId() {
        var expectedProducts = List.of(em.find(Product.class, 1L));
        var actualProducts = productRepository.findByCategoryCategoryId(1L);
        assertThat(actualProducts).containsExactlyElementsOf(expectedProducts);
    }

    @Test
    @DisplayName("Получение продуктов по id категории и по названию")
    public void shouldReturnProductByCategory() {
        var expectedProducts = List.of(em.find(Product.class, 1L));
        var actualProducts = productRepository.findByTitleAndCategoryCategoryId(1L, "Bosch GSR 180-Li Professional");
        assertThat(actualProducts).containsExactlyElementsOf(expectedProducts);
    }

    @Test
    @DisplayName("Получение всех продуктов")
    public void shouldReturnProductList() {
        var expectedProducts = getProducts();
        var actualProducts = productRepository.findAll();
        assertThat(actualProducts).containsExactlyElementsOf(expectedProducts);
    }

    @Test
    @DisplayName("Сохранение продукта")
    public void shouldSaveProduct() {
        var category = em.find(Category.class, 1L);
        var expectedProduct = new Product(0, "Тестовый продукт", 1000, 1, category);
        var savedProduct = productRepository.save(expectedProduct);

        assertThat(savedProduct)
                .isNotNull()
                .matches(product -> product.getProductId() > 0 )
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedProduct);

        assertThat(em.find(Product.class, savedProduct.getProductId()))
                .isNotNull()
                .isEqualTo(savedProduct);
    }

    @Test
    @DisplayName("Обновление продукта")
    public void shouldUpdateProduct() {
        var category = em.find(Category.class, 1L);
        var expectedProduct = new Product(1L, "Обновленный продукт", 18000, 1, category);

        assertThat(em.find(Product.class, expectedProduct.getProductId()))
                .isNotNull()
                .isNotEqualTo(expectedProduct);
        var updatedProduct = productRepository.save(expectedProduct);

        assertThat(updatedProduct)
                .isNotNull()
                .matches(product -> product.getProductId() > 0 )
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedProduct);

        assertThat(em.find(Product.class, updatedProduct.getProductId()))
                .isNotNull()
                .isEqualTo(updatedProduct);
    }

    @Test
    @DisplayName("Удаление продукта")
    public void shouldDeleteProduct() {
        assertThat(em.find(Product.class, 1L)).isNotNull();
        productRepository.deleteById(1L);
        assertThat(em.find(Product.class, 1L)).isNull();
    }

    @Test
    @DisplayName("Уменьшение количества продукта")
    public void shouldReduceQuantity() {
        var category = em.find(Category.class, 1L);
        var expectedProduct = new Product(1L, "Bosch GSR 180-Li Professional", 18000, 1, category);
        assertThat(em.find(Product.class, expectedProduct.getProductId()))
                .isNotNull()
                .isNotEqualTo(expectedProduct);

        productRepository.reduceQuantity(1, expectedProduct.getProductId());
        var productWithReducedQuantity = em.find(Product.class, expectedProduct.getProductId());

        assertThat(productWithReducedQuantity)
                .isNotNull()
                .isEqualTo(expectedProduct);
    }



    public List<Product> getProducts() {
        return List.of(em.find(Product.class, 1),
                em.find(Product.class, 2),
                em.find(Product.class, 3));
    }
}
