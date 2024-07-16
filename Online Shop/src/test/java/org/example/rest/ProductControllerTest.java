package org.example.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.CategoryDto;
import org.example.model.dto.ProductDto;
import org.example.model.dto.ProductNewDto;
import org.example.model.dto.ProductUpdateDto;
import org.example.service.ProductService;
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

import java.util.List;
import java.util.Optional;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;


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
    @DisplayName("Получение всех продуктов")
    public void shouldReturnProductList() throws Exception {
        var returnedProducts = getProductsDto();

        Mockito.when(productService.findAll()).thenReturn(returnedProducts);

        mvc.perform(get("/api/products")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bosch GSR 180-Li Professional")))
                .andExpect(content().string(containsString("Дрели")))
                .andExpect(content().string(containsString("Дисковая пила Makita HS301DZ")))
                .andExpect(content().string(containsString("Пилы")))
                .andExpect(content().string(containsString("Воздуходувка портативная беспроводная аккумуляторная")))
                .andExpect(content().string(containsString("Компрессоры")));
    }

    @Test
    @DisplayName("Получение продукта по id")
    public void shouldReturnProductById() throws Exception {
        var returnedProduct = Optional.of(new ProductDto(1, "Bosch GSR 180-Li Professional", 18000, 1,
                new CategoryDto(1, "Дрели")));

        Mockito.when(productService.findById(1L)).thenReturn(returnedProduct);

        mvc.perform(get("/api/products/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bosch GSR 180-Li Professional")))
                .andExpect(content().string(containsString("18000")))
                .andExpect(content().string(containsString("Дрели")));
    }

    @Test
    @DisplayName("Получение продукта по названию")
    public void shouldReturnProductByTitle() throws Exception {
        var returnedProduct = List.of(new ProductDto(1, "Bosch GSR 180-Li Professional", 18000, 1,
                new CategoryDto(1, "Дрели")));

        Mockito.when(productService.findByTitle("Bosch GSR 180-Li Professional")).thenReturn(returnedProduct);

        mvc.perform(get("/api/products/categories/0?product-title=Bosch GSR 180-Li Professional")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bosch GSR 180-Li Professional")))
                .andExpect(content().string(containsString("18000")))
                .andExpect(content().string(containsString("Дрели")));
    }

    @Test
    @DisplayName("Сохранение продукта")
    public void shouldSaveProduct() throws Exception {
        var savedProduct = new ProductNewDto("Тестовый продукт", 5000, 1, 1L);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedProduct)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление продукта")
    public void shouldUpdateProduct() throws Exception {
        var updatedProduct = new ProductUpdateDto(1L,"Тестовый продукт", 18000, 1,1L);

        mvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление продукта")
    public void shouldDeleteProduct() throws Exception {
        mvc.perform(delete("/api/products/1")).andDo(print())
                .andExpect(status().isOk());
    }


    public List<ProductDto> getProductsDto() {
        return List.of(new ProductDto(1, "Bosch GSR 180-Li Professional", 18000, 1, new CategoryDto(1, "Дрели")),
                new ProductDto(2, "Дисковая пила Makita HS301DZ", 7790, 1, new CategoryDto(2, "Пилы")),
                new ProductDto(3, "Воздуходувка портативная беспроводная аккумуляторная", 2184, 1, new CategoryDto(3, "Компрессоры")));
    }

}
