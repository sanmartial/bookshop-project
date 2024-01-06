package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.order.OrderItemDto;
import com.globaroman.bookshopproject.dto.order.OrderRequestDto;
import com.globaroman.bookshopproject.dto.order.OrderResponseDto;
import com.globaroman.bookshopproject.dto.order.OrderStatusDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundCustomException;
import com.globaroman.bookshopproject.mapper.OrderItemMapper;
import com.globaroman.bookshopproject.mapper.OrderMapper;
import com.globaroman.bookshopproject.model.Order;
import com.globaroman.bookshopproject.model.OrderItem;
import com.globaroman.bookshopproject.model.ShoppingCart;
import com.globaroman.bookshopproject.model.User;
import com.globaroman.bookshopproject.repository.OrderItemRepository;
import com.globaroman.bookshopproject.repository.OrderRepository;
import com.globaroman.bookshopproject.repository.ShoppingCartRepository;
import com.globaroman.bookshopproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper itemMapper;

    @Override
    public OrderResponseDto addOrder(
            OrderRequestDto orderRequestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Order order = getOrderFromShoppingCart(user, orderRequestDto);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getAllOrder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can't find order with id: " + orderId));
        OrderItem orderItem = order.getOrderItems()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundCustomException(
                                "Can't find orderItem with id:" + itemId));
        return itemMapper.toDto(orderItem);
    }

    @Override
    public OrderResponseDto updateStatusToOrder(OrderStatusDto statusDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can't find order with id: " + id));
        order.setStatus(statusDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> getOrderItensFromOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundCustomException("Can't find order with id: " + orderId));
        return orderMapper.orderItemsToDtos(order.getOrderItems());
    }

    private Order getOrderFromShoppingCart(User user, OrderRequestDto orderRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Can't find shopping cart for user with id: " + user.getId()));
        Order order = getOrderWithFields(shoppingCart, orderRequestDto);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        shoppingCartRepository.delete(shoppingCart);
        return order;
    }

    private Order getOrderWithFields(
            ShoppingCart shoppingCart,
            OrderRequestDto orderRequestDto) {

        Order order = orderMapper.shoppingCartToOrder(shoppingCart);
        order.setShippingAddress(orderRequestDto.shippingAddress());
        Order orderSave = orderRepository.save(order);

        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(itemMapper::cartItemToOrderItem)
                .peek(o -> o.setOrder(orderSave))
                .map(this::addItemToDb)
                .collect(Collectors.toSet());

        orderSave.setTotal(getTotalCostOrder(orderItems));
        orderSave.setOrderItems(orderItems);
        return orderSave;
    }

    private BigDecimal getTotalCostOrder(Set<OrderItem> orderItems) {
        double sum = orderItems.stream()
                .mapToDouble(o ->
                        o.getPrice().doubleValue() * o.getQuantity())
                .sum();
        return new BigDecimal(sum);
    }

    private OrderItem addItemToDb(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
