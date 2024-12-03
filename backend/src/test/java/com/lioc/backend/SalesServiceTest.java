package com.lioc.backend;

import com.lioc.backend.model.Inventory;
import com.lioc.backend.model.Product;
import com.lioc.backend.model.Sales;
import com.lioc.backend.repository.InventoryRepository;
import com.lioc.backend.repository.SalesRepository;
import com.lioc.backend.service.SalesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private SalesService salesService;

    @Test
    public void testAddSales_Success() {
        Sales sales = new Sales();
        sales.setSalesId(1);
        sales.setMachine("Machine A");
        sales.setQty(5.0);

        Product product = new Product();
        product.setProductId(101);
        sales.setProduct(product);

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQty(10.0);

        // Mock repository behavior
        when(inventoryRepository.findByProduct_ProductId(101)).thenReturn(inventory);
        when(salesRepository.save(any(Sales.class))).thenReturn(sales);

        String result = salesService.addSales(sales);

        assertEquals("Sales added Successfully", result);
        assertEquals(5.0, inventory.getQty()); // Verify inventory is reduced
        verify(salesRepository, times(1)).save(sales);
        verify(inventoryRepository, times(1)).findByProduct_ProductId(101);
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    public void testUpdateSales_Success() {
        Sales existingSales = new Sales();
        existingSales.setSalesId(1);
        existingSales.setMachine("Machine A");
        existingSales.setQty(5.0);

        Sales updatedSales = new Sales();
        updatedSales.setMachine("Updated Machine");
        updatedSales.setQty(10.0);

        Product product = new Product();
        product.setProductId(101);
        updatedSales.setProduct(product);

        // Mock repository behavior
        when(salesRepository.findById(1)).thenReturn(Optional.of(existingSales));
        when(salesRepository.save(any(Sales.class))).thenReturn(existingSales);

        String result = salesService.updateSales(updatedSales, 1);

        assertEquals("Sales Updated Successfully", result);
        assertEquals("Updated Machine", existingSales.getMachine());
        assertEquals(10.0, existingSales.getQty());
        verify(salesRepository, times(1)).findById(1);
        verify(salesRepository, times(1)).save(existingSales);
    }


}

