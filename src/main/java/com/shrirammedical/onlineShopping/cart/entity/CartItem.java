package com.shrirammedical.onlineShopping.cart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_cartitem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long productId;
    private Integer quantity;
    private Double price; // snapshot of price

    @ManyToOne
    private Cart cart;

}
