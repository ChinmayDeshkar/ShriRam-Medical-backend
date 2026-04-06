package com.shrirammedical.onlineShopping.cart.dto;

import com.shrirammedical.onlineShopping.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {
    private List<CartItemsDto> items;
    private Double totalAmount;
}
