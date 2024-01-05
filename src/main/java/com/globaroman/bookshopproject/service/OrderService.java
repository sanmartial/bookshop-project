package com.globaroman.bookshopproject.service;

import java.util.List;
import com.globaroman.bookshopproject.dto.order.OrderItemDto;
import com.globaroman.bookshopproject.dto.order.OrderRequestDto;
import com.globaroman.bookshopproject.dto.order.OrderResponseDto;
import com.globaroman.bookshopproject.dto.order.OrderStatusDto;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderResponseDto addOrder(
            OrderRequestDto orderRequestDto,
            Authentication authentication);

    List<OrderResponseDto> getAllOrder(Authentication authentication);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);

    OrderResponseDto updateStatusToOrder(OrderStatusDto statusDto, Long id);

    List<OrderItemDto> getOrderItensFromOrder(Long orderId);
}
