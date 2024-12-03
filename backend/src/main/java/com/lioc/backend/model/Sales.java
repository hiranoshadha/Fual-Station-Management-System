package com.lioc.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
@Entity
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesid")
    private int salesId;

    @Column(name = "machine")
    @NotBlank(message = "Machine Name Required")
    private String machine;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "qty")
    private double qty;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = new Date();
        }
    }

}
