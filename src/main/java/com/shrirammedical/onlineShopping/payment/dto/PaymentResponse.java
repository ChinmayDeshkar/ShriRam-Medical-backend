package com.shrirammedical.onlineShopping.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String gatewayOrderId;
    private Double amount;
    private String key;
}
