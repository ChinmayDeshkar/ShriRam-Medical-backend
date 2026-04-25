package com.shrirammedical.onlineShopping.product.controller;

import com.shrirammedical.onlineShopping.product.dto.CategoryDto;
import com.shrirammedical.onlineShopping.product.dto.CategoryUpdateRequest;
import com.shrirammedical.onlineShopping.product.entity.Category;
import com.shrirammedical.onlineShopping.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    List<CategoryDto> getAllCategory(){
        return categoryService.findAllCategory();
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/add")
    ResponseEntity<?> addProduct(@RequestBody Category category){
        categoryService.addCategory(category);
        return ResponseEntity.ok(Map.of("Message", "Category Added Successfully"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/update/{categoryId}")
    ResponseEntity<?> updateCategory(@PathVariable Long categoryId,
                                     @RequestBody CategoryUpdateRequest request){
        try {
            log.info("Coming here");
            categoryService.updateCategory(categoryId, request);
            return ResponseEntity.ok(Map.of("Message", "Product updated"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @DeleteMapping("/delete/{categoryId}")
    ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(Map.of("Message", "Category Deleted"));
    }
}
