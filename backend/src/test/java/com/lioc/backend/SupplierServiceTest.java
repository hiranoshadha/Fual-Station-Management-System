package com.lioc.backend;

import com.lioc.backend.model.Supplier;
import com.lioc.backend.repository.SupplierRepository;
import com.lioc.backend.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    public void testAddSupplier_Success() {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1);
        supplier.setName("Test Supplier");
        supplier.setMobile("1234567890");

        // Mock repository behavior
        when(supplierRepository.findBySupplierId(1)).thenReturn(null);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        String result = supplierService.addSupplier(supplier);

        assertEquals("Supplier added Successfully", result);
        verify(supplierRepository, times(1)).findBySupplierId(1);
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    public void testUpdateSupplier_Success() {
        Supplier existingSupplier = new Supplier();
        existingSupplier.setSupplierId(1);
        existingSupplier.setName("Old Supplier");
        existingSupplier.setMobile("1111111111");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setName("Updated Supplier");
        updatedSupplier.setMobile("9999999999");

        // Mock repository behavior
        when(supplierRepository.findBySupplierId(1)).thenReturn(existingSupplier);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(existingSupplier);

        String result = supplierService.updateSupplier(updatedSupplier, 1);

        assertEquals("Supplier Updated Successfully", result);
        assertEquals("Updated Supplier", existingSupplier.getName());
        assertEquals("9999999999", existingSupplier.getMobile());
        verify(supplierRepository, times(1)).findBySupplierId(1);
        verify(supplierRepository, times(1)).save(existingSupplier);
    }


}
