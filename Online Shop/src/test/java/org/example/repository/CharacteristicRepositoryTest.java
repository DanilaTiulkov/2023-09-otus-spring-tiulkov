package org.example.repository;

import org.example.model.Brand;
import org.example.model.Category;
import org.example.model.Characteristic;
import org.example.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@DataJpaTest
public class CharacteristicRepositoryTest {

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("Поиск характеристики по Id продукта")
    public void shouldReturnCharacteristicByProductId() {
        var expectedCharacteristic = em.find(Characteristic.class, 1L);
        var actualCharacteristic = characteristicRepository.findByProductProductId(1L);

        assertThat(actualCharacteristic)
                .isNotNull()
                .isEqualTo(expectedCharacteristic);
    }

    @Test
    @DisplayName("Сохранение характеристики")
    public void shouldSaveCharacteristic() {
        var product = new Product(0, "Тестовый продукт", 1500, 1, getCategories().get(1));
        em.persist(product);
        var expectedCharacteristic = new Characteristic(0, 2, "Серый", "20х200х225", getBrands().get(1), product);
        var savedCharacteristic = characteristicRepository.save(expectedCharacteristic);

        assertThat(savedCharacteristic)
                .isNotNull()
                .matches(characteristic -> characteristic.getCharacteristicId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedCharacteristic);

        assertThat(em.find(Characteristic.class, savedCharacteristic.getCharacteristicId()))
                .isNotNull()
                .isEqualTo(savedCharacteristic);

    }

    @Test
    @DisplayName("Обновление характеристики")
    public void shouldUpdateCharacteristic() {
        var product = em.find(Product.class, 1L);
        var expectedCharacteristic = new Characteristic(1, 2, "Серый", "20х200х225", getBrands().get(1), product);

        assertThat(em.find(Characteristic.class,expectedCharacteristic.getCharacteristicId()))
                .isNotNull()
                .isNotEqualTo(expectedCharacteristic);

        var updatedCharacteristic = characteristicRepository.save(expectedCharacteristic);
        assertThat(updatedCharacteristic)
                .isNotNull()
                .matches(characteristic -> characteristic.getCharacteristicId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedCharacteristic);

        assertThat(em.find(Characteristic.class, updatedCharacteristic.getCharacteristicId()))
                .isNotNull()
                .isEqualTo(updatedCharacteristic);
    }

    public List<Brand> getBrands() {
        return List.of(em.find(Brand.class, 1),
                em.find(Brand.class, 2),
                em.find(Brand.class, 3));
    }

    public List<Category> getCategories() {
        return List.of(em.find(Category.class, 1),
                em.find(Category.class, 2),
                em.find(Category.class, 3));
    }
}
