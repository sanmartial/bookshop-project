package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.ShoppingCart;
import com.globaroman.bookshopproject.model.User;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    void setUp() {
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setFirstName("fn");
        newUser.setLastName("ln");
        newUser.setShippingAddress("LA");
        newUser.setPassword("testPassword");
        userRepository.save(newUser);

        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(newUser);
        shoppingCartRepository.save(newShoppingCart);
    }

    @Test
    @DisplayName("Find by existing User id")
    @Transactional
    @Rollback
    void findByUserId_Id_ExistingUserId_ReturnUserShoppingCart() {
        Optional<User> userOptional = userRepository.findByEmail("test@example.com");
        Assertions.assertTrue(userOptional.isPresent());
        User actual = userOptional.get();

        Optional<ShoppingCart> shoppingCartOptional
                = shoppingCartRepository.findByUserId(actual.getId());
        Assertions.assertTrue(shoppingCartOptional.isPresent());
        User expected = shoppingCartOptional.get().getUser();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find by no existing User id")
    @Transactional
    @Rollback
    public void findByUser_Id_NonExistingUserId_ReturnEmptyOptional() {
        Long nonExistingUserId = 100L;

        Optional<ShoppingCart> shoppingCartOptional
                = shoppingCartRepository.findByUserId(nonExistingUserId);

        Assertions.assertTrue(shoppingCartOptional.isEmpty());
    }
}
