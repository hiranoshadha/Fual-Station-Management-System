package com.lioc.backend.service;

import com.lioc.backend.model.Employee;
import com.lioc.backend.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
public class EmployeeService {
    
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Find all
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    //Search by userId
    public Employee searchEmployeeByUserId(int uid) {
        Employee e = employeeRepository.findByUser_UserId(uid);
        if (e == null) {
            log.error("Searched for a non-existing employee");
            throw new NoSuchElementException("No Employee Found with the UID : " + uid);
        } else {
            log.info("Searched for an employee");
            return e;
        }
    }
}
