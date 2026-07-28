package com.ekart.service.impl;

import com.ekart.common.dto.OrderItemResponse;
import com.ekart.common.dto.OrderResponse;
import com.ekart.common.entity.*;
import com.ekart.exception.ResourceNotFoundException;
import com.ekart.repository.*;
import com.ekart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponse checkout() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .status("PLACED")
                .totalAmount(java.math.BigDecimal.ZERO)
                .build();

        order = orderRepository.save(order);

        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        product.getName() + " is out of stock"
                );
            }

            product.setStock(
                    product.getStock() - cartItem.getQuantity()
            );

            productRepository.save(product);

            java.math.BigDecimal itemTotal =
                    product.getPrice().multiply(
                            java.math.BigDecimal.valueOf(
                                    cartItem.getQuantity()
                            )
                    );

            total = total.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }
        order.setTotalAmount(total);

        orderRepository.save(order);

        cartItemRepository.deleteAll(cart.getItems());

        cart.getItems().clear();

        cartRepository.save(cart);

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = getCurrentUser();

        return orderRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        User user = getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found");
        }

        return mapToResponse(order);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(item ->
                                OrderItemResponse.builder()
                                        .productId(item.getProduct().getId())
                                        .productName(item.getProduct().getName())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .total(
                                                item.getPrice().multiply(
                                                        java.math.BigDecimal.valueOf(item.getQuantity())
                                                )
                                        )
                                        .build()
                        )
                        .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}