package org.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.BrandDto;
import org.example.model.dto.CharacteristicDto;
import org.example.model.dto.CharacteristicNewDto;
import org.example.model.dto.CharacteristicUpdateDto;
import org.example.service.CharacteristicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacteristicController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CharacteristicControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CharacteristicService characteristicService;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    @DisplayName("Получение характеристики по id продукта")
    public void shouldReturnCharacteristicByProductId() throws Exception {
        var brandDto = new BrandDto(1, "Bosh", "Германия");
        var expectedCharacteristic = new CharacteristicDto(1, 2, "Голубой", "198х62х225", brandDto);

        Mockito.when(characteristicService.findByProductId(1)).thenReturn(expectedCharacteristic);

        mvc.perform(get("/api/products/1/characteristics")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"batteryCapacity\":2")))
                .andExpect(content().string(containsString("\"color\":\"Голубой\"")))
                .andExpect(content().string(containsString("\"size\":\"198х62х225\"")))
                .andExpect(content().string(containsString("\"country\":\"Германия\"")));
    }

    @Test
    @DisplayName("Сохранение характеристики")
    public void shouldSaveCharacteristic() throws Exception {
        var savedCharacteristic = new CharacteristicNewDto(2, "Белый", "108х42х225", 1L, 4L);
        mvc.perform(post("/api/characteristics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedCharacteristic)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление характеристики")
    public void shouldUpdateCharacteristic() throws Exception {
        var updatedCharacteristic = new CharacteristicUpdateDto(1L,2, "Белый", "108х42х225", 1L, 4L);
        mvc.perform(put("/api/characteristics/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCharacteristic)))
                .andExpect(status().isOk());
    }
}
