package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.order.OrderItemDto;
import com.globaroman.bookshopproject.model.CartItem;
import com.globaroman.bookshopproject.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface OrderItemMapper {
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "book", qualifiedByName = "getPriceFromBook")
    OrderItem cartItemToOrderItem(CartItem cartItem);

    @Mapping(source = "book", target = "bookId", qualifiedByName = "bookFromId")
    OrderItemDto toDto(OrderItem orderItem);
}
