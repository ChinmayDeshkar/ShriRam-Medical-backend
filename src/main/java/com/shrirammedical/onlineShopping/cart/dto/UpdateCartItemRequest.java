package com.shrirammedical.onlineShopping.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequest {
    private Long itemId;
    private Integer quantity;

}
