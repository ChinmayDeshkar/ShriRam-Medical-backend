package com.shrirammedical.onlineShopping.order.service;

import com.shrirammedical.onlineShopping.order.dto.OrderRequestDto;
import com.shrirammedical.onlineShopping.order.entity.Order;

import java.util.List;

public interface OrderService {
    // create order
    Order createOrder(OrderRequestDto order);

    // Update Order
    Order updateOrder(Order order);

    // Get order by userid
    List<Order> getAllOrders();

    // Get Order by id
    Order getOrderById(Long id);
}
