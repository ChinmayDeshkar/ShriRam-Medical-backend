package com.shrirammedical.onlineShopping.order.repository;

import com.shrirammedical.onlineShopping.order.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.orderStatus = :status WHERE o.orderId = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId,
                          @Param("status") String status);

    List<Order> findOrderByUserId(String userId);
}
