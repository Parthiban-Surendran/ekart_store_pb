package com.ekart.repository;

import com.ekart.common.entity.Cart;
import com.ekart.common.entity.CartItem;
import com.ekart.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(
            Cart cart,
            Product product
    );
}