package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.config.PasswordEncoderConfig;
import com.globaroman.bookshopproject.dto.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.UserResponseDto;
import com.globaroman.bookshopproject.exception.RegistrationException;
import com.globaroman.bookshopproject.mapper.UserMapper;
import com.globaroman.bookshopproject.model.Role;
import com.globaroman.bookshopproject.model.User;
import com.globaroman.bookshopproject.repository.RoleRepository;
import com.globaroman.bookshopproject.repository.UserRepository;
import com.globaroman.bookshopproject.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Long ROLE_ID_IN_DB =
            (long) Role.RoleName.USER.ordinal() + 1;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    "Can't register user. The same user with email "
                            + requestDto.getEmail()
                            + " already exist");
        }

        User user = getUserWithRoleAndPasswordEncode(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    private User getUserWithRoleAndPasswordEncode(UserRegistrationRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoderConfig
                .passwordEncoder().encode(requestDto.getPassword()));
        user.setRoles(Set.of(getRoleFromDB(ROLE_ID_IN_DB)));
        return user;
    }

    private Role getRoleFromDB(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Can't find role with id: " + id));
    }
}
