package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.cart.CarItemDto;
import com.globaroman.bookshopproject.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(source = "book", target = "bookId", qualifiedByName = "bookFromId")
    @Mapping(source = "book", target = "bookTitle", qualifiedByName = "getTitleFromBook")
    CarItemDto toCartItemDto(CartItem cartItem);

}
