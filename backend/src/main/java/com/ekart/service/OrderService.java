package com.ekart.service;

import com.ekart.common.dto.CheckoutRequest;
import com.ekart.common.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);
}