package com.shrirammedical.onlineShopping.order.repository;

import com.shrirammedical.onlineShopping.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
