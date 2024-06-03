package org.example.security;


import org.example.model.dto.BrandDto;
import org.example.rest.BrandController;
import org.example.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(BrandController.class)
@Import(SecurityConfiguration.class)
public class BrandControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        Mockito.when(brandService.findBrands()).thenReturn(getBrandsDto());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("Успешный тест доступности для админа")
    public void shouldReturnOkForAdminRole() throws Exception {
        mvc.perform(get("/api/brands")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @DisplayName("Отказ в доступе. Роль: User")
    public void shouldDeniedWithRoleUser() throws Exception {
        mvc.perform(get("/api/brands")).andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("Редирект на login page. Роль: БЕЗ РОЛИ")
    public void shouldRedirectWithoutRole() throws Exception {
        mvc.perform(get("/api/brands")).andDo(print())
                .andExpect(status().is(302));
    }


    public List<BrandDto> getBrandsDto() {
        return List.of(new BrandDto(1, "Bosh", "Германия"),
                new BrandDto(2, "Mackita", "Япония"),
                new BrandDto(3, "Bork", "Китай"));
    }
}
