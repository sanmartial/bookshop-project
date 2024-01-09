package com.globaroman.bookshopproject.controller;

import com.globaroman.bookshopproject.dto.order.OrderItemDto;
import com.globaroman.bookshopproject.dto.order.OrderRequestDto;
import com.globaroman.bookshopproject.dto.order.OrderResponseDto;
import com.globaroman.bookshopproject.dto.order.OrderStatusDto;
import com.globaroman.bookshopproject.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management",
        description = "endpoint for order management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Add new order",
            description = "You can creat a new order")
    public OrderResponseDto addOrder(
            @RequestBody OrderRequestDto requestDto,
            Authentication authentication) {
        return orderService.addOrder(requestDto, authentication);
    }

    @GetMapping
    @Operation(summary = "Get all order",
            description = "You can get all order as history")
    public List<OrderResponseDto> getAllOrder(Authentication authentication) {
        return orderService.getAllOrder(authentication);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update status order",
            description = "You can update an order")
    public OrderResponseDto updateOrderStatus(
            @RequestBody OrderStatusDto statusDto,
            @PathVariable Long id) {
        return orderService.updateStatusToOrder(statusDto, id);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all item by order",
            description = "You can get all orders from specific order")
    public List<OrderItemDto> getOrderItemsFromOrder(@PathVariable Long orderId) {
        return orderService.getOrderItensFromOrder(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get item by order",
            description = "You can get order from specific order")
    public OrderItemDto getOrderItemById(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
