package com.shrirammedical.onlineShopping.cart.dto;

import com.shrirammedical.onlineShopping.cart.entity.Cart;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemsDto {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price; // snapshot of price


}
