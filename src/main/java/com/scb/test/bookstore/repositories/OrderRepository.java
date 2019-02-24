package com.scb.test.bookstore.repositories;

import com.scb.test.bookstore.domain.Order;
import com.scb.test.bookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
