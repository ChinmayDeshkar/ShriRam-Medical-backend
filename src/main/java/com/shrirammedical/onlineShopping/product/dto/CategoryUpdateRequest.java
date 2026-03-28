package com.shrirammedical.onlineShopping.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryUpdateRequest {
    private String categoryName;
    private String description;
}
