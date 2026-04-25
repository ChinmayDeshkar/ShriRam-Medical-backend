package com.shrirammedical.onlineShopping.product.service;

import com.shrirammedical.onlineShopping.product.dto.ProductUpdateRequest;
import com.shrirammedical.onlineShopping.product.dto.SearchProductRequest;
import com.shrirammedical.onlineShopping.product.entity.Products;

import java.util.List;

public interface ProductService {

    List<Products> getAllProducts();
    Products addProduct(Products product, List<String> imageUrls);
    List<Products> searchProducts(SearchProductRequest searchProduct);
    Products updateProduct(Long productId, ProductUpdateRequest request);
    void deleteProduct(Long productId);
    List<Products> getProductsByProductType(Long productType);
}
