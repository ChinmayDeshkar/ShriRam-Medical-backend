package com.shrirammedical.onlineShopping.cart.service;

import com.shrirammedical.onlineShopping.cart.dto.AddToCartRequest;
import com.shrirammedical.onlineShopping.cart.dto.CartResponse;
import com.shrirammedical.onlineShopping.cart.dto.UpdateCartItemRequest;
import com.shrirammedical.onlineShopping.cart.entity.Cart;
import com.shrirammedical.onlineShopping.cart.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart addToCart(AddToCartRequest request);
    CartResponse getCart();
    void updateCartItem(UpdateCartItemRequest request);
}
