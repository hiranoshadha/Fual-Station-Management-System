package com.lioc.backend;

import com.lioc.backend.dto.CustomerRegisterDTO;
import com.lioc.backend.model.Customer;
import com.lioc.backend.repository.CustomerRepository;
import com.lioc.backend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testAddCustomer_Success() {
        CustomerRegisterDTO customerDTO = new CustomerRegisterDTO();
        customerDTO.setName("John Doe");
        customerDTO.setNic("123456789V");
        customerDTO.setMobile("0771234567");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPassword("password");

        Customer customerEntity = customerDTO.DTOToEntity();

        // Mock repository behavior
        when(customerRepository.findByNic(customerEntity.getNic())).thenReturn(null);
        when(customerRepository.save(any(Customer.class))).thenReturn(customerEntity);

        String result = customerService.addCustomer(customerDTO);

        assertEquals("Customer added Successfully", result);
        verify(customerRepository, times(1)).findByNic(customerEntity.getNic());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testAddCustomer_Failure() {
        CustomerRegisterDTO customerDTO = new CustomerRegisterDTO();
        customerDTO.setName("Jane Doe");
        customerDTO.setNic("987654321V");
        customerDTO.setMobile("0779876543");
        customerDTO.setEmail("janedoe@example.com");
        customerDTO.setPassword("password");

        Customer existingCustomer = customerDTO.DTOToEntity();

        // Mock repository behavior
        when(customerRepository.findByNic(existingCustomer.getNic())).thenReturn(existingCustomer);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> customerService.addCustomer(customerDTO)
        );

        assertEquals("customer found with the NIC : 987654321V", exception.getMessage());
        verify(customerRepository, times(1)).findByNic(existingCustomer.getNic());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer_Success() {
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(1);
        existingCustomer.setName("John Doe");
        existingCustomer.setNic("123456789V");
        existingCustomer.setMobile("0771234567");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setMobile("0777654321");

        // Mock repository behavior
        when(customerRepository.findByNic("123456789V")).thenReturn(existingCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        String result = customerService.updateCustomer(updatedCustomer, "123456789V");

        assertEquals("Customer Updated Successfully", result);
        assertEquals("0777654321", existingCustomer.getMobile());
        verify(customerRepository, times(1)).findByNic("123456789V");
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    public void testUpdateCustomer_Failure() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setMobile("0777654321");

        // Mock repository behavior
        when(customerRepository.findByNic("123456789V")).thenReturn(null);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> customerService.updateCustomer(updatedCustomer, "123456789V")
        );

        assertEquals("customer not found with the NIC : 123456789V", exception.getMessage());
        verify(customerRepository, times(1)).findByNic("123456789V");
        verify(customerRepository, never()).save(any(Customer.class));
    }


}

