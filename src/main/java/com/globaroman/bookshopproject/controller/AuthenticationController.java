package com.globaroman.bookshopproject.controller;

import com.globaroman.bookshopproject.dto.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.UserResponseDto;
import com.globaroman.bookshopproject.exception.RegistrationException;
import com.globaroman.bookshopproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
