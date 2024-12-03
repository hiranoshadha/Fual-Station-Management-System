package com.lioc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lioc.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

    Product findByProductId(int productId);
    
}
