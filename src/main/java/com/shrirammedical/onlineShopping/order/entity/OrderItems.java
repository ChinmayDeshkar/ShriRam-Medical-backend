package com.shrirammedical.onlineShopping.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_orderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long productId;
    private String productName;
    private String productDescription;
    private int quantity;
    private Double productPrice;
    private Double totalPrice;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

}
