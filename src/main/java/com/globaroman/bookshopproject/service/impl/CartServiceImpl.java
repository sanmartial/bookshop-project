package com.globaroman.bookshopproject.service.impl;

import com.globaroman.bookshopproject.dto.cart.CartItemRequestDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import com.globaroman.bookshopproject.exception.EntityNotFoundCustomException;
import com.globaroman.bookshopproject.mapper.ShoppingCartMapper;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.CartItem;
import com.globaroman.bookshopproject.model.ShoppingCart;
import com.globaroman.bookshopproject.model.User;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CartItemRepository;
import com.globaroman.bookshopproject.repository.ShoppingCartRepository;
import com.globaroman.bookshopproject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto addBook(
            CartItemRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);

        Book requestedBook = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Can't find book with id: " + requestDto.getBookId()));

        CartItem cartItem = new CartItem();
        cartItem.setBook(requestedBook);
        cartItem.setQuantity(requestDto.getQuantity());

        cartItem.setShoppingCart(shoppingCart);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        shoppingCart.getCartItems().add(savedCartItem);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ShoppingCart shoppingCart = getShoppingCartFromDbOrNew(user);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto update(Long cartItemId, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundCustomException(
                        "Can't find cart with id: "
                                + cartItemId)
        );
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        Long shoppingCartId = cartItem.getShoppingCart().getId();
        return shoppingCartMapper.toShoppingCartDto(shoppingCartRepository
                .findById(shoppingCartId).orElseThrow(
                        () -> new EntityNotFoundCustomException(
                                "Can't find cart with id: "
                                        + shoppingCartId)));
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getShoppingCartFromDbOrNew(User user) {
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    ShoppingCart newShoppingCart = new ShoppingCart();
                    newShoppingCart.setUser(user);
                    return shoppingCartRepository.save(newShoppingCart);
                });
    }
}
