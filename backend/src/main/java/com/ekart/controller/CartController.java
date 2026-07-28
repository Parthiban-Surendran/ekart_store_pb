package com.ekart.controller;

import com.ekart.common.dto.AddToCartRequest;
import com.ekart.common.dto.CartResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ekart.common.dto.UpdateCartItemRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @Valid @RequestBody AddToCartRequest request) {

        CartResponse response = cartService.addToCart(request);

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .success(true)
                        .message("Product added to cart")
                        .data(response)
                        .build()
        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {

        CartResponse response = cartService.getCart();

        return ResponseEntity.ok(
                ApiResponse.<CartResponse>builder()
                        .success(true)
                        .message("Cart fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/items/{itemId}")
    public ApiResponse<CartResponse> updateQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        CartResponse response =
                cartService.updateQuantity(itemId, request);

        return ApiResponse.<CartResponse>builder()
                .success(true)
                .message("Cart quantity updated successfully")
                .data(response)
                .build();
    }

    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> removeItem(
            @PathVariable Long itemId
    ) {

        cartService.removeItem(itemId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Cart item removed successfully")
                .build();
    }

    @DeleteMapping
    public ApiResponse<Void> clearCart() {

        cartService.clearCart();

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Cart cleared successfully")
                .build();
    }
}