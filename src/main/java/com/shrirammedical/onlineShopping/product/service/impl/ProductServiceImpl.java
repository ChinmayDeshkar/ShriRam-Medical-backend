package com.shrirammedical.onlineShopping.product.service.impl;

import com.shrirammedical.onlineShopping.config.JwtUtil;
import com.shrirammedical.onlineShopping.product.dto.ProductUpdateRequest;
import com.shrirammedical.onlineShopping.product.dto.SearchProductRequest;
import com.shrirammedical.onlineShopping.product.entity.Category;
import com.shrirammedical.onlineShopping.product.entity.Products;
import com.shrirammedical.onlineShopping.product.repository.CategoryRepo;
import com.shrirammedical.onlineShopping.product.repository.ProductRepo;
import com.shrirammedical.onlineShopping.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Products addProduct(Products product) {

        log.info("Adding product: " + product);

        // throw an error if something is some
        if(product.getProductName() == null || product.getRate() == null
            || product.getStock() == null || product.getCategoryId() == null){
            throw new RuntimeException("Something getting null while inserting product");
        }

        product.setCreatedBy(JwtUtil.getCurrentUser());
        return productRepo.save(product);
    }

    @Override
    public List<Products> searchProducts(SearchProductRequest searchProduct) {

        log.info("Searching for product with parameters: " + searchProduct);
        if (searchProduct.getProductId() != null) {
            return List.of(productRepo.findById(searchProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("Not found")));
        }

        if (searchProduct.getCategoryId() != null) {
            return productRepo.findByCategoryId(searchProduct.getCategoryId());
        }

        if (searchProduct.getProductName() != null) {
            return productRepo.findByProductNameContainingIgnoreCase(searchProduct.getProductName());
        }

        return productRepo.findAll();
    }

    @Override
    public Products updateProduct(Long productId, ProductUpdateRequest request) {

        log.info("Updating product with Product id = " + productId);
        Products product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found by id: " + productId));

        if (request.getRate() != null) {
            product.setRate(request.getRate());
        }

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }

        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }

        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        if (request.getCategoryId() != null) {
            boolean isCategoryPresent = categoryRepo.existsById(request.getCategoryId());
            if(isCategoryPresent){
                product.setCategoryId(request.getCategoryId());
            }else {
                throw new RuntimeException("Category does not present, id = " + request.getCategoryId());
            }
        }

        product.setUpdatedBy(JwtUtil.getCurrentUser());
        product.setUpdatedDate(LocalDateTime.now());
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {

        log.info("Deleting product with id = " + productId);
        productRepo.deleteById(productId);
    }
}
