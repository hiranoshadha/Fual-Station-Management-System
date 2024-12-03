package com.lioc.backend.service;

import com.lioc.backend.model.Inventory;
import com.lioc.backend.model.Sales;
import com.lioc.backend.repository.InventoryRepository;
import com.lioc.backend.repository.SalesRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
public class SalesService {
    private SalesRepository salesRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository, InventoryRepository inventoryRepository) {
        this.salesRepository = salesRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Sales> getAll() {
        return salesRepository.findAll();
    }

    public Sales searchSales(int salesId) {
        Sales sales = salesRepository.findById(salesId).orElse(null);
        if (sales == null) {
            log.error("Searched for a non-existing sales record");
            throw new NoSuchElementException("No Sales Found with ID: " + salesId);
        }
        log.info("Searched for a sales record");
        return sales;
    }

    public String addSales(Sales sales) {
        try {
            salesRepository.save(sales);
            Inventory i = inventoryRepository.findByProduct_ProductId(sales.getProduct().getProductId());
            i.setQty(i.getQty()-sales.getQty());
            inventoryRepository.save(i);
            log.info("Added new sales record");
            return "Sales added Successfully";
        } catch (Exception e) {
            log.error("Error adding sales record");
            throw new RuntimeException("Error adding sales record: " + e.getMessage());
        }
    }

    public String updateSales(Sales sales, int salesId) {
        try {
            Sales s = salesRepository.findById(salesId).orElse(null);
            if (s != null) {
                s.setMachine(sales.getMachine());
                s.setProduct(sales.getProduct());
                s.setDate(sales.getDate());
                s.setQty(sales.getQty());
                salesRepository.save(s);
                log.info("Updated a sales record");
                return "Sales Updated Successfully";
            }
            throw new NoSuchElementException("Sales not found with ID: " + salesId);
        } catch (IllegalArgumentException e) {
            log.error("Tried to update a non-existing sales record");
            throw new NoSuchElementException("Sales not found with ID: " + salesId);
        }
    }

    public String deleteSales(int salesId) {
        Sales s = salesRepository.findById(salesId).orElse(null);
        if (s == null) {
            throw new NoSuchElementException("Sales not found with ID: " + salesId);
        }
        salesRepository.delete(s);
        return "Sales Deleted Successfully";
    }
}