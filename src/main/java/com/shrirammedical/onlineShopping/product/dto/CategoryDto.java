package com.shrirammedical.onlineShopping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String description;
}
