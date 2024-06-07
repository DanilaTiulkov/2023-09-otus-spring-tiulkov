package org.example.service;

import org.example.mapper.BrandMapper;
import org.example.model.Brand;
import org.example.model.dto.BrandDto;
import org.example.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class BrandServiceImplTest {

    @MockBean
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandService brandService;

    private List<Brand> dbBrands;


    @BeforeEach
    public void init() {
        dbBrands = getBrands();
    }

    @Test
    @DisplayName("Успешное получение всех брендов")
    public void shouldReturnBrandsList() {
        Mockito.when(brandRepository.findAll()).thenReturn(dbBrands);
        var expectingBrands = getBrandsDto();
        var actualBrands = brandService.findBrands();
        assertThat(actualBrands).containsExactlyElementsOf(expectingBrands);
    }

    public List<Brand> getBrands() {
        return List.of(new Brand(1, "Bosh", "Германия"),
                new Brand(2, "Mackita", "Япония"),
                new Brand(3, "Bork", "Китай"));
    }

    public List<BrandDto> getBrandsDto() {
        return List.of(new BrandDto(1, "Bosh", "Германия"),
                new BrandDto(2, "Mackita", "Япония"),
                new BrandDto(3, "Bork", "Китай"));
    }
}
