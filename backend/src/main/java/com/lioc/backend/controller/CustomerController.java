package com.lioc.backend.controller;

import com.lioc.backend.dto.CustomerRegisterDTO;
import com.lioc.backend.model.Customer;
import com.lioc.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@CrossOrigin
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping(value = "/search/nic/{nic}")
    public ResponseEntity<Customer> getByNic(@PathVariable String nic) {
        return ResponseEntity.ok(customerService.searchCustomer(nic));
    }

    @GetMapping(value = "/search/uid/{uid}")
    public ResponseEntity<Customer> getByUserId(@PathVariable int uid) {
        return ResponseEntity.ok(customerService.searchCustomerByUserId(uid));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        return ResponseEntity.ok(customerService.addCustomer(customerRegisterDTO));
    }

    @PutMapping(value = "/update/{nic}")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable String nic) {
        return ResponseEntity.ok(customerService.updateCustomer(customer, nic));
    }

    @DeleteMapping(value = "/delete/{nic}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String nic) {
        return ResponseEntity.ok(customerService.deleteCustomer(nic));
    }
}