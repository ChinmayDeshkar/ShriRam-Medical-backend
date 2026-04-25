package com.shrirammedical.onlineShopping.product.controller;

import com.shrirammedical.onlineShopping.product.dto.ProductUpdateRequest;
import com.shrirammedical.onlineShopping.product.dto.SearchProductRequest;
import com.shrirammedical.onlineShopping.product.entity.Products;
import com.shrirammedical.onlineShopping.product.service.ImageUploadService;
import com.shrirammedical.onlineShopping.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ImageUploadService imageUploadService;

    @GetMapping("/all")
    List<Products> getAllProducts(){
        try {
            return productService.getAllProducts();
        } catch (Exception e) {
            log.error("Error while fetching all products, " + e);
            throw new RuntimeException(e);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> addProduct(
            @RequestPart("product") Products product,
            @RequestPart("images") List<MultipartFile> images){
        try {
            List<String> imageUrls = imageUploadService.uploadImage(images);
            productService.addProduct(product, imageUrls);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message", "Product added"));
        } catch (Exception e) {
            log.error("Error occurred while adding product, " + e);
            throw new RuntimeException("Error: " + e);
        }
    }

    @GetMapping("/search")
    List<Products> searchProduct(@RequestParam(required = false) Long productId,
                                 @RequestParam(required = false) String productName,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) Boolean active){

        SearchProductRequest request = createSearchProductRequest(productId, productName, categoryId, active);
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

//    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/delete/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        try{
            log.info("Coming here");
            productService.deleteProduct(productId);
            return ResponseEntity.ok(Map.of("Message", "Product Deleted"));
        } catch (Exception e) {
            log.error("Error while deleting product, id = " + productId);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE', 'CUSTOMER')")
    @GetMapping("/getByCategory/{categoryId}")
    List<Products> getProductsByCategory(@PathVariable Long categoryId) {
        try {
            return productService.getProductsByProductType(categoryId);
        } catch (Exception e) {
            log.error("Error while fetching products by category, category id = " + categoryId);
            throw new RuntimeException(e);
        }
    }


    // ------------------------ Helper Methods ----------------------- //
    private SearchProductRequest createSearchProductRequest(Long productId, String productName, Long categoryId, Boolean active) {
        SearchProductRequest request = new SearchProductRequest();
        request.setProductId(productId);
        request.setProductName(productName);
        request.setCategoryId(categoryId);
        request.setActive(active);
        return request;
    }
}
