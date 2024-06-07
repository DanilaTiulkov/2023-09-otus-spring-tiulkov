package org.example.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.BrandDto;
import org.example.model.dto.CharacteristicDto;
import org.example.model.dto.CharacteristicNewDto;
import org.example.model.dto.CharacteristicUpdateDto;
import org.example.rest.CharacteristicController;
import org.example.service.CharacteristicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

@WebMvcTest(CharacteristicController.class)
@Import(SecurityConfiguration.class)
public class CharacteristicControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CharacteristicService characteristicService;

    @BeforeEach
    void setUp() {
        var brandDto = new BrandDto(1, "Bosh", "Германия");
        var characteristicDto = new CharacteristicDto(1, 2, "Голубой", "198х62х225", brandDto);
        Mockito.when(characteristicService.findByProductId(1)).thenReturn(characteristicDto);
    }


    @Test
    @DisplayName("Успешный тест get метода доступности для админа")
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void shouldReturnOkForAdminRoleMethodGet() throws Exception {
        mvc.perform(get("/api/products/1/characteristics")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест get метода доступности для юзера")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldReturnOkForUserRoleMethodGet() throws Exception {
        mvc.perform(get("/api/products/1/characteristics")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест get метода доступности без роли")
    public void shouldReturnOkWithoutRoleMethodGet() throws Exception {
        mvc.perform(get("/api/products/1/characteristics")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест put метода доступности для админа")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldReturnOkForAdminRoleMethodPut() throws Exception {
        var characteristic = new CharacteristicUpdateDto(1L,2, "Белый", "108х42х225", 1L, 4L);
        mvc.perform(put("/api/characteristics/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(characteristic)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный тест post метода доступности для админа")
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    public void shouldReturnOkForAdminRoleMethodPost() throws Exception {
        var characteristic = new CharacteristicNewDto(2, "Белый", "108х42х225", 1L, 4L);
        mvc.perform(post("/api/characteristics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(characteristic)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("factory")
    @DisplayName("Отказ в доступе к методам put и post. Роль: USER")
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void shouldDeniedForUserRoleMethodPutAndPost(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(403));
    }

    @ParameterizedTest
    @MethodSource("factory")
    @DisplayName("Отказ в доступе к методам put и post. Роль: без роли")
    public void shouldRedirectWithoutRoleMethodPutAndPost(MockHttpServletRequestBuilder builder) throws Exception {
        mvc.perform(builder).andDo(print())
                .andExpect(status().is(302));
    }


    public static Stream<MockHttpServletRequestBuilder> factory() {
        return Stream.of(post("/api/characteristics"),
                put("/api/characteristics/1"));
    }
}
