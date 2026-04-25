package com.shrirammedical.onlineShopping.payment.dto;

import lombok.Data;

@Data
public class VerifyPaymentRequest {
    private String gatewayOrderId;
    private String gatewayPaymentId;
    private String signature;
}
