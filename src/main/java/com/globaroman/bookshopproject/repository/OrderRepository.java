package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByUserId(Long id);

    List<Order> findAllByUserId(Long id);
}
