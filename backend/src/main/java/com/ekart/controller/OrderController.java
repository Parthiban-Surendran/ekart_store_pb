package com.ekart.controller;

import com.ekart.common.dto.OrderResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ApiResponse<OrderResponse> checkout() {

        OrderResponse response = orderService.checkout();

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order placed successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getMyOrders() {

        return ApiResponse.<List<OrderResponse>>builder()
                .success(true)
                .message("Orders fetched successfully")
                .data(orderService.getMyOrders())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(
            @PathVariable Long id) {

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order fetched successfully")
                .data(orderService.getOrderById(id))
                .build();
    }
}