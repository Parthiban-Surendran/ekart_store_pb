package com.ekart.service;

import com.ekart.common.dto.PaymentRequest;
import com.ekart.common.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(
            Long orderId,
            PaymentRequest request
    );

    PaymentResponse getPaymentByOrderId(Long orderId);
}