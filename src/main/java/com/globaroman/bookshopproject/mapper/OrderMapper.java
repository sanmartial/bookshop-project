package com.globaroman.bookshopproject.mapper;

import com.globaroman.bookshopproject.config.MapperConfig;
import com.globaroman.bookshopproject.dto.order.OrderItemDto;
import com.globaroman.bookshopproject.dto.order.OrderResponseDto;
import com.globaroman.bookshopproject.model.Order;
import com.globaroman.bookshopproject.model.OrderItem;
import com.globaroman.bookshopproject.model.ShoppingCart;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = UserMapper.class)
public interface OrderMapper {

    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "total", constant = "0.00")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    Order shoppingCartToOrder(ShoppingCart shoppingCart);

    @Mapping(source = "user", target = "userId", qualifiedByName = "idFromUser")
    OrderResponseDto toDto(Order order);

    List<OrderItemDto> orderItemsToDtos(Set<OrderItem> orderItems);

    default OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        Long id = orderItem.getId();
        int quantity = orderItem.getQuantity();
        Long bookId = orderItem.getBook() != null ? orderItem.getBook().getId() : null;

        return new OrderItemDto(id, bookId, quantity);
    }
}
