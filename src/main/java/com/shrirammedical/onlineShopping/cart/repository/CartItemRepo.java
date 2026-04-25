package com.shrirammedical.onlineShopping.cart.repository;

import com.shrirammedical.onlineShopping.cart.entity.Cart;
import com.shrirammedical.onlineShopping.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductIdAndCart(Long productId, Cart cart);
    void deleteByProductIdAndCart(Long productId, Cart cart);


}
