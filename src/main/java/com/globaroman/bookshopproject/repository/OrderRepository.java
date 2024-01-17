package com.globaroman.bookshopproject.repository;

import com.globaroman.bookshopproject.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long id);
}
