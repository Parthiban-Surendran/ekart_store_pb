package com.ekart.common.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {

    private Long id;

    private Long orderId;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionId;

    private BigDecimal amount;
}