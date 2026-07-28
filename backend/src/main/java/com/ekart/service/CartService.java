package com.ekart.service;

import com.ekart.common.dto.AddToCartRequest;
import com.ekart.common.dto.CartResponse;
import com.ekart.common.dto.UpdateCartItemRequest;


public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse getCart();

    CartResponse updateQuantity(
            Long itemId,
            UpdateCartItemRequest request
    );

    void removeItem(Long itemId);

    void clearCart();
}