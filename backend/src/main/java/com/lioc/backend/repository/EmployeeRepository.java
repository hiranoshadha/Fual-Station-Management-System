package com.lioc.backend.repository;

import com.lioc.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByUser_UserId(int userId);
}
