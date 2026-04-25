package com.shrirammedical.onlineShopping.payment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_payment")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String gatewayOrderId;   // Razorpay order id
    private String gatewayPaymentId; // Razorpay payment id

    private Double amount;
    private String paymentStatus;
    private String paymentMethod;
}
