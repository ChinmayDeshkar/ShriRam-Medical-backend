package com.shrirammedical.onlineShopping.order.controller;

import com.shrirammedical.onlineShopping.order.dto.OrderRequestDto;
import com.shrirammedical.onlineShopping.order.entity.Order;
import com.shrirammedical.onlineShopping.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order createOrder(@RequestBody OrderRequestDto request) {
        log.info("Create Order" + request);
        return orderService.createOrder(request);
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getOrderByUser(@RequestParam String userId) {
        List<Order> orders = orderService.getAllOrders();
        if(orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("response", "No Orders Found"));
        }
        return ResponseEntity.ok(orders);
    }
}
