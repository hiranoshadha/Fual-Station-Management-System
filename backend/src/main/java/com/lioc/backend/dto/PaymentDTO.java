package com.lioc.backend.dto;


import com.lioc.backend.model.Payment;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {

    private String nic;
    private double amount;

    public Payment DTOToEntity() {

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setDate(new Date());

        return payment;
    }

}
