package com.shrirammedical.onlineShopping.product.controller;

import com.shrirammedical.onlineShopping.product.dto.ProductUpdateRequest;
import com.shrirammedical.onlineShopping.product.dto.SearchProductRequest;
import com.shrirammedical.onlineShopping.product.entity.Products;
import com.shrirammedical.onlineShopping.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/add")
    ResponseEntity<?> addProduct(@RequestBody Products product){
        try {
            productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message", "Product added"));
        } catch (Exception e) {
            log.error("Error occured while adding product, " + e);
            throw new RuntimeException("Error: " + e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CUSTOMER')")
    @GetMapping("/search")
    List<Products> searchProduct(@RequestBody SearchProductRequest request){
        try{
            return productService.searchProducts(request);
        } catch (Exception e) {
            log.error("Error while searching product with parameters, " + request);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/update/{productId}")
    ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                    @RequestBody ProductUpdateRequest request){
        try{
            productService.updateProduct(productId, request);
            return ResponseEntity.ok(Map.of("Message", "Product updated, id = " + productId));
        } catch (Exception e) {
            log.error("Error while updating product, id = " + productId);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/delete?id={productId}")
    ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        try{
            productService.deleteProduct(productId);
            return ResponseEntity.ok(Map.of("Message", "Product Deleted"));
        } catch (Exception e) {
            log.error("Error while deleting product, id = " + productId);
            throw new RuntimeException(e);
        }
    }
}
