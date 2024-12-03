package com.lioc.backend.repository;

import com.lioc.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    Payment findByPaymentId(int paymentId);
}
