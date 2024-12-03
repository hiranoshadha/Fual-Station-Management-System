package com.lioc.backend.service;

import com.lioc.backend.dto.PaymentDTO;
import com.lioc.backend.model.Customer;
import com.lioc.backend.model.Payment;
import com.lioc.backend.repository.CustomerRepository;
import com.lioc.backend.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment searchPayment(int paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId);
        if (payment == null) {
            log.error("Searched for a non-existing payment");
            throw new NoSuchElementException("No Payment Found with ID: " + paymentId);
        }
        log.info("Searched for a payment");
        return payment;
    }

    public String addPayment(Payment payment) {

        Customer c = customerRepository.findByNic(payment.getNic());
        if (c == null) {
            log.error("Customer not found");
            throw new NoSuchElementException("Customer not found");
        }

        c.setCreditAmount(c.getCreditAmount() - payment.getAmount());
        payment.setDate(new Date());

        paymentRepository.save(payment);
        customerRepository.save(c);
        log.info("Added new payment");
        return "Payment added Successfully";
    }

    public String updatePayment(Payment payment, int paymentId) {
        try {
            Payment p = paymentRepository.findByPaymentId(paymentId);
            if (p != null) {
                p.setAmount(payment.getAmount());
                paymentRepository.save(p);
                log.info("Updated a payment");
                return "Payment Updated Successfully";
            }
            throw new NoSuchElementException("Payment not found with ID: " + paymentId);
        } catch (IllegalArgumentException e) {
            log.error("Tried to update a non-existing payment");
            throw new NoSuchElementException("Payment not found with ID: " + paymentId);
        }
    }

    public String deletePayment(int paymentId) {
        Payment p = paymentRepository.findByPaymentId(paymentId);
        if (p == null) {
            throw new NoSuchElementException("Payment not found with ID: " + paymentId);
        }
        paymentRepository.delete(p);
        return "Payment Deleted Successfully";
    }
}
