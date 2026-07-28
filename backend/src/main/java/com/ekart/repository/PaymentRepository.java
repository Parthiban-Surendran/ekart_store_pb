package com.ekart.repository;

import com.ekart.common.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {


    Optional<Payment> findByOrderId(Long orderId);

}