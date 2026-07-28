package com.ekart.service.impl;

import com.ekart.common.dto.PaymentRequest;
import com.ekart.common.dto.PaymentResponse;
import com.ekart.common.entity.Order;
import com.ekart.common.entity.Payment;
import com.ekart.exception.ResourceNotFoundException;
import com.ekart.repository.OrderRepository;
import com.ekart.repository.PaymentRepository;
import com.ekart.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    @Override
    public PaymentResponse createPayment(
            Long orderId,
            PaymentRequest request
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );


        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(
                        request.getPaymentMethod()
                                .equalsIgnoreCase("COD")
                                ? "PENDING"
                                : "SUCCESS"
                ).transactionId(generateTransactionId())
                .amount(order.getTotalAmount())
                .build();


        paymentRepository.save(payment);


        return mapToResponse(payment);
    }


    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found")
                );


        return mapToResponse(payment);
    }


    private String generateTransactionId() {

        return "TXN-" + UUID.randomUUID()
                .toString()
                .substring(0,8)
                .toUpperCase();
    }


    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .amount(payment.getAmount())
                .build();
    }
}