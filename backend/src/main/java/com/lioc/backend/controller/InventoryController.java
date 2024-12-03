package com.lioc.backend.controller;

import com.lioc.backend.model.Inventory;
import com.lioc.backend.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/search/{inventoryId}")
    public ResponseEntity<Inventory> searchInventory(@PathVariable int inventoryId) {
        return ResponseEntity.ok(inventoryService.searchInventory(inventoryId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.addInventory(inventory));
    }

    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<String> updateInventory(@RequestBody Inventory inventory, @PathVariable int inventoryId) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventory, inventoryId));
    }

    @DeleteMapping("/delete/{inventoryId}")
    public ResponseEntity<String> deleteInventory(@PathVariable int inventoryId) {
        return ResponseEntity.ok(inventoryService.deleteInventory(inventoryId));
    }
}