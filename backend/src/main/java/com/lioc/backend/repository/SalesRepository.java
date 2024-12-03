package com.lioc.backend.repository;

import com.lioc.backend.model.Sales;
import com.lioc.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales,Integer> {
}
