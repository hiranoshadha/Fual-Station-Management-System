package com.lioc.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierid")
    private int supplierId;

    @Column(name = "name")
    @NotBlank(message = "Name Required")
    private String name;

    @Column(name = "mobile")
    @NotBlank(message = "Mobile Required")
    private String mobile;

    @ManyToMany
    @JoinTable(
            name = "supplier_supply_product",
            joinColumns = @JoinColumn(name = "supplierid"),
            inverseJoinColumns = @JoinColumn(name = "productid")
    )
    private List<Product> products = new ArrayList<>();

}
