package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.user.UserRegistrationRequestDto;
import com.globaroman.bookshopproject.dto.user.UserResponseDto;
import com.globaroman.bookshopproject.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
