package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByUserId(Long id);

    List<Order> findAllByUserId(Long id);
}
