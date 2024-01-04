package com.globaroman.bookshopproject.service;

import com.globaroman.bookshopproject.dto.cart.CartItemRequestDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import org.springframework.security.core.Authentication;

public interface CartService {

    ShoppingCartDto getShoppingCart(Authentication authentication);

    ShoppingCartDto addBook(
            CartItemRequestDto requestDto,
            Authentication authentication);

    ShoppingCartDto update(
            Long cartItemId,
            CartItemRequestDto requestDto);

    void deleteById(Long id);
}
