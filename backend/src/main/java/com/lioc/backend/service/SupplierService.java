package com.lioc.backend.service;

import com.lioc.backend.model.Supplier;
import com.lioc.backend.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
public class SupplierService {
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier searchSupplier(int supplierId) {
        Supplier supplier = supplierRepository.findBySupplierId(supplierId);
        if (supplier == null) {
            log.error("Searched for a non-existing supplier");
            throw new NoSuchElementException("No Supplier Found with ID: " + supplierId);
        }
        log.info("Searched for a supplier");
        return supplier;
    }

    public String addSupplier(Supplier supplier) {
        Supplier s = supplierRepository.findBySupplierId(supplier.getSupplierId());
        if (s == null) {
            supplierRepository.save(supplier);
            log.info("Added new supplier");
            return "Supplier added Successfully";
        }
        log.error("Inserted an already existing supplier");
        throw new NoSuchElementException("Supplier found with ID: " + supplier.getSupplierId());
    }

    public String updateSupplier(Supplier supplier, int supplierId) {
        try {
            Supplier s = supplierRepository.findBySupplierId(supplierId);
            if (s != null) {
                s.setName(supplier.getName());
                s.setMobile(supplier.getMobile());
                s.setProducts(supplier.getProducts());
                supplierRepository.save(s);
                log.info("Updated a supplier");
                return "Supplier Updated Successfully";
            }
            throw new NoSuchElementException("Supplier not found with ID: " + supplierId);
        } catch (IllegalArgumentException e) {
            log.error("Tried to update a non-existing supplier");
            throw new NoSuchElementException("Supplier not found with ID: " + supplierId);
        }
    }

    public String deleteSupplier(int supplierId) {
        Supplier s = supplierRepository.findBySupplierId(supplierId);
        if (s == null) {
            throw new NoSuchElementException("Supplier not found with ID: " + supplierId);
        }
        supplierRepository.delete(s);
        return "Supplier Deleted Successfully";
    }
}
