package com.globaroman.bookshopproject.controller;

import com.globaroman.bookshopproject.dto.cart.CartItemRequestDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import com.globaroman.bookshopproject.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management",
        description = "endpoint for shopping cart management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Add new books",
            description = "You can add new books to your cart")
    public ShoppingCartDto addBook(
            @RequestBody CartItemRequestDto requestDto,
            Authentication authentication) {
        return cartService.addBook(requestDto, authentication);
    }

    @PutMapping("cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update new quantity books",
            description = "You can update quantity into your cart")
    public ShoppingCartDto update(
            @RequestBody CartItemRequestDto requestDto,
            @PathVariable Long cartItemId) {
        return cartService.update(cartItemId, requestDto);
    }

    @DeleteMapping("cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete books",
            description = "You can delete your books from cart")
    public void delete(@PathVariable Long cartItemId) {
        cartService.deleteById(cartItemId);
    }

    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "You can get all selected books from your cart")
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        return cartService.getShoppingCart(authentication);
    }
}
