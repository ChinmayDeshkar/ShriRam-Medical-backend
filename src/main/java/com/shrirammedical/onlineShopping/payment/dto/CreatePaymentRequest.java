package com.shrirammedical.onlineShopping.payment.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {

    private Long orderId;
    private Double amount;
}
