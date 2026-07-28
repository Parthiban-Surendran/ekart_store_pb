package com.ekart.repository;

import com.ekart.common.entity.Order;
import com.ekart.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);
}