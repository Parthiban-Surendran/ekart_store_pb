package com.ekart.service.impl;

import com.ekart.common.dto.AddToCartRequest;
import com.ekart.common.dto.CartItemResponse;
import com.ekart.common.dto.CartResponse;
import com.ekart.common.entity.*;
import com.ekart.repository.*;
import com.ekart.service.CartService;
import com.ekart.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ekart.common.dto.UpdateCartItemRequest;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .build()
                        )
                );


        Product product = productRepository.findByIdAndActiveTrue(
                request.getProductId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Product not found")
        );


        CartItem cartItem =
                cartItemRepository.findByCartAndProduct(cart, product)
                        .orElse(
                                CartItem.builder()
                                        .cart(cart)
                                        .product(product)
                                        .quantity(0)
                                        .build()
                        );


        cartItem.setQuantity(
                cartItem.getQuantity() + request.getQuantity()
        );


        cartItemRepository.save(cartItem);


        return mapToResponse(
                cartRepository.findById(cart.getId())
                        .orElseThrow()
        );
    }

    @Override
    public CartResponse updateQuantity(
            Long itemId,
            UpdateCartItemRequest request
    ) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found")
                );


        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found")
                );


        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException(
                    "Cart item does not belong to user"
            );
        }


        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);


        return mapToResponse(cart);
    }


    @Override
    public CartResponse getCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found")
                );

        return mapToResponse(cart);
    }


    private User getCurrentUser() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }

    @Override
    public void clearCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found")
                );

        cartItemRepository.deleteAll(cart.getItems());

        cart.getItems().clear();

        cartRepository.save(cart);
    }


    private CartResponse mapToResponse(Cart cart) {

        List<CartItemResponse> items =
                cart.getItems() == null
                        ? List.of()
                        : cart.getItems()
                        .stream()
                        .map(item ->
                                CartItemResponse.builder()
                                        .id(item.getId())
                                        .productId(item.getProduct().getId())
                                        .productName(item.getProduct().getName())
                                        .price(item.getProduct().getPrice())
                                        .quantity(item.getQuantity())
                                        .total(
                                                item.getProduct().getPrice()
                                                        .multiply(
                                                                BigDecimal.valueOf(
                                                                        item.getQuantity()
                                                                )
                                                        )
                                        )
                                        .build()
                        )
                        .toList();


        BigDecimal total =
                items.stream()
                        .map(CartItemResponse::getTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );


        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .totalAmount(total)
                .build();
    }

    @Override
    public void removeItem(Long itemId) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found")
                );

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found")
                );

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new ResourceNotFoundException(
                    "Cart item does not belong to user"
            );
        }

        cart.getItems().remove(cartItem);

        cartItemRepository.delete(cartItem);
    }
}