package com.lioc.backend.repository;

import com.lioc.backend.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    Supplier findBySupplierId(int supplierId);
}
