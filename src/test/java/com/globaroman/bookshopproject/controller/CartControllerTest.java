package com.globaroman.bookshopproject.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaroman.bookshopproject.dto.cart.CarItemDto;
import com.globaroman.bookshopproject.dto.cart.CartItemRequestDto;
import com.globaroman.bookshopproject.dto.cart.ShoppingCartDto;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Category;
import com.globaroman.bookshopproject.repository.BookRepository;
import com.globaroman.bookshopproject.repository.CategoryRepository;
import com.globaroman.bookshopproject.service.CartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Add a book and its quantity and create a new ShoppingCart")
    @Transactional
    @Rollback
    void addBook__ValidShoppingCartRequestDtoAndUserId_Successful() throws Exception {

        Book book = createTestBookTemplateWithCategory();
        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setBookId(book.getId());
        requestDto.setQuantity(2);

        ShoppingCartDto expectedResponse = new ShoppingCartDto();

        expectedResponse.setCarItems(List.of(new CarItemDto(1L, book.getId(),
                book.getTitle(), requestDto.getQuantity())));

        Mockito.when(cartService.addBook(requestDto, null))
                .thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.userId")
                        .value(expectedResponse.getUserId()));
    }

    @Test
    @DisplayName("Remove book from cart and if successful, return status Ok")
    void delete_ValidCartItemId_Successful() throws Exception {
        doNothing().when(cartService).deleteById(anyLong());

        mockMvc.perform(delete("/api/cart/cart-items/{cartItemId}", Mockito.anyLong()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

    @Test
    @DisplayName("Get user's shopping cart, successful return status Ok")
    void getShoppingCart() throws Exception {
        ShoppingCartDto mockShoppingCart = new ShoppingCartDto();
        when(cartService.getShoppingCart(null)).thenReturn(mockShoppingCart);

        mockMvc.perform(get("/api/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper.writeValueAsString(mockShoppingCart)));
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Category Description");
        return categoryRepository.save(category);
    }

    private Book createTestBookTemplateWithCategory() {

        Category category = createTestCategory();
        Book book = new Book();
        book.setTitle("Existing Book");
        book.getCategories().add(category);
        book.setAuthor("Existing Author");
        book.setIsbn("9-781-34119-1");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setDescription("Existing Description");
        book.setCoverImage("existing_cover.jpg");
        return bookRepository.save(book);
    }
}
