package com.shrirammedical.onlineShopping.product.service;

import com.shrirammedical.onlineShopping.product.dto.CategoryUpdateRequest;
import com.shrirammedical.onlineShopping.product.entity.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);
    List<Category> findAllCategory();
    void deleteCategory(Long categoryId);
    Category updateCategory(Long categoryId, CategoryUpdateRequest request);
}
