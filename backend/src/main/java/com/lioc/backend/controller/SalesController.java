package com.lioc.backend.controller;

import com.lioc.backend.model.Sales;
import com.lioc.backend.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@CrossOrigin
public class SalesController {

    private SalesService salesService;

    @Autowired
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sales>> getAllSales() {
        return ResponseEntity.ok(salesService.getAll());
    }

    @GetMapping("/search/{salesId}")
    public ResponseEntity<Sales> searchSales(@PathVariable int salesId) {
        return ResponseEntity.ok(salesService.searchSales(salesId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSales(@RequestBody Sales sales) {
        return ResponseEntity.ok(salesService.addSales(sales));
    }

    @PutMapping("/update/{salesId}")
    public ResponseEntity<String> updateSales(@RequestBody Sales sales, @PathVariable int salesId) {
        return ResponseEntity.ok(salesService.updateSales(sales, salesId));
    }

    @DeleteMapping("/delete/{salesId}")
    public ResponseEntity<String> deleteSales(@PathVariable int salesId) {
        return ResponseEntity.ok(salesService.deleteSales(salesId));
    }
}
