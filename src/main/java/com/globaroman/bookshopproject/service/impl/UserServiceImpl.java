package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.UserResponseDto;
import com.globaroman.bookshopproject.exception.RegistrationException;
import com.globaroman.bookshopproject.mapper.UserMapper;
import com.globaroman.bookshopproject.repository.UserRepository;
import com.globaroman.bookshopproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {

            throw new RegistrationException(
                    "Can't register user. The same user with email "
                            + requestDto.getEmail()
                            + " already exist");
        }
        return userMapper.toDto(userRepository.save(userMapper.toModel(requestDto)));
    }
}
