package com.shrirammedical.onlineShopping.order.dto;

import lombok.Data;

@Data
public class OrderItemEvent {
    private Long productId;
    private Integer quantity;
}
