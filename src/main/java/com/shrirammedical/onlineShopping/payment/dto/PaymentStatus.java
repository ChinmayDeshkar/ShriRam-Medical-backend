package com.shrirammedical.onlineShopping.payment.dto;

import lombok.Data;

@Data
public class PaymentStatus {
    public static final String PENDING_PAYMENT = "PENDING";
    public static final String COMPLETED = "COMPLETED";
    public static final String FAILED = "FAILED";
    public static final String CANCELLED = "CANCELLED";

}
