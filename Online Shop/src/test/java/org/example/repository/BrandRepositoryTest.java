package org.example.repository;

import org.example.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private TestEntityManager em;

    List<Brand> dbBrands;

    @BeforeEach
    public void init() {
        dbBrands = getBrands();
    }

    @Test
    @DisplayName("Поиск всех брендов")
    public void shouldReturnBrandList() {
        var expectedBrands = getBrands();
        var actualBands = brandRepository.findAll();
        assertThat(actualBands).containsExactlyElementsOf(expectedBrands);
    }

    public List<Brand> getBrands() {
        return List.of(em.find(Brand.class, 1),
                em.find(Brand.class, 2),
                em.find(Brand.class, 3));
    }
}
