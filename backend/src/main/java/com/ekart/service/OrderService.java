package com.ekart.service;

import com.ekart.common.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse checkout();

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);
}