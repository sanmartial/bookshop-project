package com.globaroman.bookshopproject.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaroman.bookshopproject.dto.user.UserLoginRequestDto;
import com.globaroman.bookshopproject.dto.user.UserLoginResponseDto;
import com.globaroman.bookshopproject.dto.user.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.user.UserResponseDto;
import com.globaroman.bookshopproject.service.AuthenticationService;
import com.globaroman.bookshopproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("User registration must be successful")
    void register_ValidData_ShouldSuccessfulRegistration() throws Exception {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "test@example.com",
                "password",
                "password",
                "John",
                "Doe",
                "123 Main St"
        );
        when(userService.register(any())).thenReturn(new UserResponseDto(
                1L,
                "test@example.com",
                "John",
                "Doe",
                "123 Main St"));
        mockMvc.perform(post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("User login must be successful")
    void login_ValidData_SuccessfulLogin() throws Exception {
        UserLoginRequestDto userLoginRequestDto =
                new UserLoginRequestDto("test@example.com", "password");
        when(authenticationService.authenticate(any()))
                .thenReturn(new UserLoginResponseDto("testIsToken"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isAccepted());
    }
}
