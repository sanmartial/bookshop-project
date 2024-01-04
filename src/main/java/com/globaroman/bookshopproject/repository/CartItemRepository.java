package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
