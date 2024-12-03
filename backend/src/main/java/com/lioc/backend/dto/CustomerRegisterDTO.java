package com.lioc.backend.dto;

import com.lioc.backend.model.Customer;
import com.lioc.backend.model.User;
import com.lioc.backend.util.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {

    private String name;
    private String nic;
    private String mobile;
    private String email;
    private String password;

    public Customer DTOToEntity() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(UserType.CUSTOMER);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setNic(nic);
        customer.setMobile(mobile);
        customer.setCreditAmount(25000);
        customer.setUser(user);

        return customer;
    }

}
