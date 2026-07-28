package com.ekart.controller;

import com.ekart.common.dto.PaymentRequest;
import com.ekart.common.dto.PaymentResponse;
import com.ekart.common.response.ApiResponse;
import com.ekart.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;


    @PostMapping("/order/{orderId}")
    public ApiResponse<PaymentResponse> createPayment(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequest request
    ) {

        PaymentResponse response =
                paymentService.createPayment(orderId, request);


        return ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment created successfully")
                .data(response)
                .build();
    }


    @GetMapping("/order/{orderId}")
    public ApiResponse<PaymentResponse> getPayment(
            @PathVariable Long orderId
    ) {

        PaymentResponse response =
                paymentService.getPaymentByOrderId(orderId);


        return ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment fetched successfully")
                .data(response)
                .build();
    }
}