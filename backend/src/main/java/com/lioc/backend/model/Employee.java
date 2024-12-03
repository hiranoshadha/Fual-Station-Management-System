package com.lioc.backend.model;

import com.lioc.backend.util.enums.EmployeeRoles;
import com.lioc.backend.util.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeid")
    private int employeeId;

    @Column(name = "name")
    @NotBlank(message = "Name Required")
    private String name;

    @Column(name = "nic")
    @NotBlank(message = "NIC Required")
    private String nic;

    @Column(name = "mobile")
    @NotBlank(message = "Mobile Required")
    private String mobile;

    @Column(name = "role", columnDefinition = "CHAR")
    @Enumerated(EnumType.STRING)
    private EmployeeRoles role;

    @OneToOne
    @JoinColumn(name = "userid")
    private User user;

}
