package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.cart.CarItemDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import com.globaroman.bookshopproject.model.CartItem;
import com.globaroman.bookshopproject.model.ShoppingCart;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "carItems")
    ShoppingCartDto toshoppingCartDto(ShoppingCart shoppingCart);

    List<CarItemDto> cartItemsToDtos(Set<CartItem> cartItems);
}
