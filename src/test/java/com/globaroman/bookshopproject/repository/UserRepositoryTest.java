package com.globaroman.bookshopproject.repository;

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
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setFirstName("fn");
        newUser.setLastName("ln");
        newUser.setShippingAddress("LA");
        newUser.setPassword("testPassword");
        userRepository.save(newUser);
    }

    @Test
    @DisplayName("existsByEmail should return true")
    @Transactional
    @Rollback
    public void existsByEmail_ExistingEmail_ReturnTrue() {
        boolean exists = userRepository.findByEmail("test@example.com").isPresent();

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("existsByEmail should return false")
    @Transactional
    @Rollback
    public void existsByEmail_NonExistingEmail_ReturnFalse() {
        boolean exists = userRepository.findByEmail("nonexistent@example.com").isPresent();

        Assertions.assertFalse(exists);
    }

    @Test
    @DisplayName("findByEmail consist Email")
    @Transactional
    @Rollback
    public void findByEmail_ExistingEmail_ReturnUser() {
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        Assertions.assertTrue(foundUser.isPresent());

        String expected = newUser.getEmail();
        String actual = foundUser.get().getEmail();
        Assertions.assertEquals(expected, actual);
    }
}
