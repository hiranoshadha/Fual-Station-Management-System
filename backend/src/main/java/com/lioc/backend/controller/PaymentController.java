package com.lioc.backend.controller;

import com.lioc.backend.dto.PaymentDTO;
import com.lioc.backend.model.Payment;
import com.lioc.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/search/{paymentId}")
    public ResponseEntity<Payment> searchPayment(@PathVariable int paymentId) {
        return ResponseEntity.ok(paymentService.searchPayment(paymentId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.addPayment(payment));
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<String> updatePayment(@RequestBody Payment payment, @PathVariable int paymentId) {
        return ResponseEntity.ok(paymentService.updatePayment(payment, paymentId));
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable int paymentId) {
        return ResponseEntity.ok(paymentService.deletePayment(paymentId));
    }
}