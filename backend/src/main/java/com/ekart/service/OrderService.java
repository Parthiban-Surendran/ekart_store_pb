package com.ekart.service;

import com.ekart.common.dto.CheckoutRequest;
import com.ekart.common.dto.OrderResponse;
import com.ekart.common.dto.UpdateOrderStatusRequest;

import java.util.List;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrderStatus(
            Long orderId,
            UpdateOrderStatusRequest request
    );

    OrderResponse cancelOrder(Long orderId);

    OrderResponse refundOrder(Long orderId);
}