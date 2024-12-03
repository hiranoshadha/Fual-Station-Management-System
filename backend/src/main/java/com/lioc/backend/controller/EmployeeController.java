package com.lioc.backend.controller;

import com.lioc.backend.model.Employee;
import com.lioc.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
@CrossOrigin
public class EmployeeController {
    
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping(value = "/search/uid/{uid}")
    public ResponseEntity<Employee> getByUserId(@PathVariable int uid) {
        return ResponseEntity.ok(employeeService.searchEmployeeByUserId(uid));
    }
}
