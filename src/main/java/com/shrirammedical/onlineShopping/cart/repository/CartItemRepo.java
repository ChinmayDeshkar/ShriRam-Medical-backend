package com.shrirammedical.onlineShopping.cart.repository;

import com.shrirammedical.onlineShopping.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
