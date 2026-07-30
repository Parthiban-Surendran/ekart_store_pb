package com.ekart.controller;

import com.ekart.common.dto.OrderResponse;
import com.ekart.common.dto.UpdateOrderStatusRequest;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {

        return ApiResponse.<List<OrderResponse>>builder()
                .success(true)
                .message("Orders fetched successfully")
                .data(orderService.getAllOrders())
                .build();
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderResponse> updateStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order status updated successfully")
                .data(orderService.updateOrderStatus(orderId, request))
                .build();
    }

    @PutMapping("/{orderId}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(
            @PathVariable Long orderId
    ) {

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order cancelled successfully")
                .data(orderService.cancelOrder(orderId))
                .build();
    }

    @PutMapping("/{orderId}/refund")
    public ApiResponse<OrderResponse> refundOrder(
            @PathVariable Long orderId
    ) {

        return ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order refunded successfully")
                .data(orderService.refundOrder(orderId))
                .build();
    }
}