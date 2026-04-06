package com.shrirammedical.onlineShopping.product.service.impl;

import com.shrirammedical.onlineShopping.product.dto.CategoryDto;
import com.shrirammedical.onlineShopping.product.dto.CategoryUpdateRequest;
import com.shrirammedical.onlineShopping.product.entity.Category;
import com.shrirammedical.onlineShopping.product.repository.CategoryRepo;
import com.shrirammedical.onlineShopping.product.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;


    @Override
    public Category addCategory(Category category) {
        log.info("Adding new category, " + category);
        return categoryRepo.save(category);
    }

    @Override
    public List<CategoryDto> findAllCategory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<CategoryDto> categoryDto = createCategoryDto(categoryRepo.findAll());
        log.info("{} searched for category", auth.getName());
        return categoryDto;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryUpdateRequest request) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category does not exist with id = " + categoryId));
        if(request.getCategoryName() != null){
            category.setCategoryName(request.getCategoryName());
        }
        if(request.getDescription() != null){
            category.setDescription(request.getDescription());
        }
        return categoryRepo.save(category);
    }


    // ------------------- Helper Methods ------------------ //
    private List<CategoryDto> createCategoryDto(List<Category> categoryList) {
        return categoryList.stream()
                .map(category ->
                        new CategoryDto(category.getCategoryId(), category.getCategoryName(), category.getDescription())).toList();
    }
}
