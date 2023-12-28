package com.globaroman.bookshopproject.service;

import com.globaroman.bookshopproject.dto.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.UserResponseDto;
import com.globaroman.bookshopproject.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
