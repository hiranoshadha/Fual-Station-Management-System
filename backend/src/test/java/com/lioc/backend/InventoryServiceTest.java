package com.lioc.backend;

import com.lioc.backend.model.Inventory;
import com.lioc.backend.repository.InventoryRepository;
import com.lioc.backend.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void testAddInventory_Success() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setQty(10.5);

        // Mock repository behavior
        when(inventoryRepository.findByInventoryId(1)).thenReturn(null);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        String result = inventoryService.addInventory(inventory);

        assertEquals("Inventory added Successfully", result);
        verify(inventoryRepository, times(1)).findByInventoryId(1);
        verify(inventoryRepository, times(1)).save(inventory);
    }

    @Test
    public void testAddInventory_Failure() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);

        // Mock repository behavior
        when(inventoryRepository.findByInventoryId(1)).thenReturn(inventory);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            inventoryService.addInventory(inventory);
        });

        assertEquals("Inventory found with ID: 1", exception.getMessage());
        verify(inventoryRepository, times(1)).findByInventoryId(1);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    public void testUpdateInventory_Success() {
        Inventory existingInventory = new Inventory();
        existingInventory.setInventoryId(1);
        existingInventory.setQty(10.0);

        Inventory updatedInventory = new Inventory();
        updatedInventory.setQty(15.0);

        // Mock repository behavior
        when(inventoryRepository.findByInventoryId(1)).thenReturn(existingInventory);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(existingInventory);

        String result = inventoryService.updateInventory(updatedInventory, 1);

        assertEquals("Inventory Updated Successfully", result);
        assertEquals(15.0, existingInventory.getQty());
        verify(inventoryRepository, times(1)).findByInventoryId(1);
        verify(inventoryRepository, times(1)).save(existingInventory);
    }

    @Test
    public void testUpdateInventory_Failure() {
        Inventory updatedInventory = new Inventory();
        updatedInventory.setQty(20.0);

        // Mock repository behavior
        when(inventoryRepository.findByInventoryId(1)).thenReturn(null);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            inventoryService.updateInventory(updatedInventory, 1);
        });

        assertEquals("Inventory not found with ID: 1", exception.getMessage());
        verify(inventoryRepository, times(1)).findByInventoryId(1);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }


}
