package com.shrirammedical.onlineShopping.product.repository;

import com.shrirammedical.onlineShopping.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {
    List<Products> findByProductType(Long productType);
    List<Products> findByProductNameContainingIgnoreCase(String productName);
}
