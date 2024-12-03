package com.lioc.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid")
    private int customerId;

    @Column(name = "name")
    @NotBlank(message = "Name Required")
    private String name;

    @Column(name = "nic")
    @NotBlank(message = "NIC Required")
    private String nic;

    @Column(name = "mobile")
    @NotBlank(message = "Mobile Required")
    private String mobile;

    @Column(name = "creditamount")
    private double creditAmount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userid")
    private User user;

}
