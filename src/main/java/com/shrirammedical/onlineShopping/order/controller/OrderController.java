package com.shrirammedical.onlineShopping.order.controller;

import com.shrirammedical.onlineShopping.order.dto.OrderRequestDto;
import com.shrirammedical.onlineShopping.order.entity.Order;
import com.shrirammedical.onlineShopping.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order createOrder(@RequestBody OrderRequestDto request) {
        return orderService.createOrder(request);

    }
}
