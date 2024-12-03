package com.lioc.backend.controller;

import com.lioc.backend.model.Supplier;
import com.lioc.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@CrossOrigin
public class SupplierController {

    private SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/search/{supplierId}")
    public ResponseEntity<Supplier> searchSupplier(@PathVariable int supplierId) {
        return ResponseEntity.ok(supplierService.searchSupplier(supplierId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.addSupplier(supplier));
    }

    @PutMapping("/update/{supplierId}")
    public ResponseEntity<String> updateSupplier(@RequestBody Supplier supplier, @PathVariable int supplierId) {
        return ResponseEntity.ok(supplierService.updateSupplier(supplier, supplierId));
    }

    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int supplierId) {
        return ResponseEntity.ok(supplierService.deleteSupplier(supplierId));
    }
}
