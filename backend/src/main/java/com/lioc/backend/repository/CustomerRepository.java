package com.lioc.backend.repository;

import com.lioc.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByCustomerId(int cid);
    Customer findByNic(String nic);
    Customer findByUser_UserId(int userId);
}
