package com.globaroman.bookshopproject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.model.Order;
import com.globaroman.bookshopproject.model.OrderItem;
import com.globaroman.bookshopproject.model.Status;
import com.globaroman.bookshopproject.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllByUserId() {
        // Given
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setShippingAddress("Shevchenko st, 17");
        userRepository.save(user);

        Book book = new Book();
        book.setTitle("Example Book");
        book.setAuthor("Author");
        book.setIsbn("9-781-34119-5");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setDescription("Book Description");
        book.setCoverImage("book_cover.jpg");
        bookRepository.save(book);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setTotal(BigDecimal.valueOf(59.98));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress("Shipping Address");
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(book);
        orderItem.setQuantity(2);
        orderItem.setPrice(BigDecimal.valueOf(29.99));
        orderItemRepository.save(orderItem);

        // When
        List<Order> orders = orderRepository.findAllByUserId(user.getId());

        // Then
        assertEquals(1, orders.size());

    }
}
