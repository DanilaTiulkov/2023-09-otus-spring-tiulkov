package org.example.service;


import org.example.mapper.CharacteristicMapper;
import org.example.model.Brand;
import org.example.model.Category;
import org.example.model.Characteristic;
import org.example.model.Product;
import org.example.model.dto.BrandDto;
import org.example.model.dto.CharacteristicDto;
import org.example.repository.BrandRepository;
import org.example.repository.CharacteristicRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.assertj.core.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CharacteristicServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private BrandRepository brandRepository;

    @MockBean
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private CharacteristicMapper characteristicMapper;

    @Autowired
    private CharacteristicServiceImpl characteristicService;

    private Characteristic characteristic;

    private Product product;

    private Brand brand;

    @BeforeEach
    public void init() {
        product = new Product(1, "Bosch GSR 180-Li Professional", 18000, 1, new Category(1, "Дрели"));
        brand = new Brand(1, "Bosh", "Германия");
        characteristic = new Characteristic(1,2, "Голубой", "198х62х225", brand, product);
    }

    @Test
    @DisplayName("Должен вернуть сохраненную характеристику")
    public void shouldReturnSavedCharacteristic() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        Mockito.when(characteristicService.save(0, 2, "Голубой", "198х62х225", 1, 1))
                .thenReturn(characteristic);
        var brandDto = new BrandDto(1, "Bosh", "Германия");
        var expectedCharacteristic = new CharacteristicDto(1, 2, "Голубой", "198х62х225", brandDto);
        var actualCharacteristic = characteristicService.insert(2, "Голубой", "198х62х225", 1, 1);
        assertThat(actualCharacteristic)
                .isNotNull()
                .isEqualTo(expectedCharacteristic);
    }
}
