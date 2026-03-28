package com.shrirammedical.onlineShopping.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductUpdateRequest {
    private String productName;
    private Double rate;
    private Long stock;
    private Long categoryId;
    private Boolean active;
}
