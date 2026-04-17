package com.shrirammedical.onlineShopping.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderPlacedEvent {
    private Long orderId;
    private String userId;
    private Double totalAmount;

    private List<OrderItemEvent> items;
}

