package com.globaroman.bookshopproject.service.impl;

import static org.mockito.ArgumentMatchers.any;

import com.globaroman.bookshopproject.dto.cart.CartItemRequestDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import com.globaroman.bookshopproject.mapper.ShoppingCartMapper;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.CartItem;
import com.globaroman.bookshopproject.model.ShoppingCart;
import com.globaroman.bookshopproject.model.User;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CartItemRepository;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import com.globaroman.bookshopproject.repository.ShoppingCartRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

class CartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CartServiceImpl shoppingCartService;
    private User user;
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User()
                .setId(1L)
                .setEmail("roman@gmail.com")
                .setPassword("$2a$10$2P9C9iZmpeNBNt2qrNKHcO7mxE/DcDV62TVvHa1OZpa1Ha3Hzi1Va")
                .setFirstName("John")
                .setLastName("Duo")
                .setShippingAddress("Shevchenko st, 17");

        shoppingCart = new ShoppingCart()
                .setId(1L)
                .setUser(user);

    }

    @Test
    @DisplayName("Add book should return shopping cart")
    void addBook_AddBookToCart_ReturnsShoppingCartResponseDto() {
        // Given
        Book requestedBook = new Book();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2);
        cartItem.setBook(requestedBook);
        cartItem.setShoppingCart(shoppingCart);

        cartItem.setShoppingCart(shoppingCart);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        Set<CartItem> setItems = new HashSet<>();
        setItems.add(savedCartItem);
        shoppingCart.setCartItems(setItems);
        long bookId = 1L;
        int quantity = 2;

        CartItemRequestDto requestDto = new CartItemRequestDto(bookId, quantity);
        Authentication authentication = new org.springframework.security
                .authentication.UsernamePasswordAuthenticationToken(user, null);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(shoppingCartRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(shoppingCart));
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book()));
        Mockito.when(cartItemRepository.save(any(CartItem.class)))
                .thenReturn(savedCartItem);
        Mockito.when(shoppingCartMapper.toShoppingCartDto(any(ShoppingCart.class)))
                .thenReturn(new ShoppingCartDto());

        // When
        ShoppingCartDto result = shoppingCartService.addBook(requestDto, authentication);

        // Then
        Assertions.assertNotNull(result);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findByUserId(user.getId());
        Mockito.verify(bookRepository, Mockito.times(1)).findById(bookId);
        Mockito.verify(shoppingCartMapper, Mockito.times(1)).toShoppingCartDto(any());
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).save(any());

    }

    @Test
    @DisplayName("Get user shopping cart return shoppingCartDto")
    void getShoppingCart_GetUserShoppingCart_ReturnsShoppingCartDto() {

        Optional<ShoppingCart> optionalShoppingCart = Optional.of(new ShoppingCart());
        Mockito.when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(optionalShoppingCart);
        Mockito.when(shoppingCartMapper.toShoppingCartDto(any())).thenReturn(new ShoppingCartDto());

        Authentication authentication = new org.springframework
                .security.authentication.UsernamePasswordAuthenticationToken(user, null);

        ShoppingCartDto result = shoppingCartService.getShoppingCart(authentication);

        Assertions.assertNotNull(result);
        Mockito.verify(shoppingCartRepository, Mockito.times(1)).findByUserId(user.getId());
        Mockito.verify(shoppingCartMapper, Mockito.times(1)).toShoppingCartDto(any());
    }

    @Test
    @DisplayName("Remove book from cart no return Value")
    void deleteById_removeBookFromCart_RemoveBookFromCart_NoReturnValue() {
        Long cartItemId = 1L;
        shoppingCartService.deleteById(cartItemId);
        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteById(cartItemId);
    }
}
