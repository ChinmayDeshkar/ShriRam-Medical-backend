package com.shrirammedical.onlineShopping.cart.repository;

import com.shrirammedical.onlineShopping.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);
}
