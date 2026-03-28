package com.shrirammedical.onlineShopping.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchProductRequest {
    private Long productId;
    private String productName;
    private Long categoryId;
    private Boolean active;

}
