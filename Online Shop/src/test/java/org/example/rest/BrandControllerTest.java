package org.example.rest;

import org.example.model.dto.BrandDto;
import org.example.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BrandService brandService;

    private List<BrandDto> brandsDto;

    @BeforeEach
    public void init() {
        brandsDto = getBrandsDto();
    }


    @Test
    @DisplayName("Получение всех брендов")
    public void shouldReturnBrandList() throws Exception {
        Mockito.when(brandService.findBrands()).thenReturn(brandsDto);
        mvc.perform(get("/api/brands")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bosh")))
                .andExpect(content().string(containsString("Mackita")))
                .andExpect(content().string(containsString("Bork")));
    }


    public List<BrandDto> getBrandsDto() {
        return List.of(new BrandDto(1, "Bosh", "Германия"),
                new BrandDto(2, "Mackita", "Япония"),
                new BrandDto(3, "Bork", "Китай"));
    }
}
