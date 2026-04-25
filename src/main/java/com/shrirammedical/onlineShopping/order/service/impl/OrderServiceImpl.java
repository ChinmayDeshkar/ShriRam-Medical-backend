package com.shrirammedical.onlineShopping.order.service.impl;

import com.shrirammedical.onlineShopping.cart.entity.Cart;
import com.shrirammedical.onlineShopping.cart.repository.CartRepo;
import com.shrirammedical.onlineShopping.order.dto.OrderItemEvent;
import com.shrirammedical.onlineShopping.order.dto.OrderPlacedEvent;
import com.shrirammedical.onlineShopping.order.dto.OrderRequestDto;
import com.shrirammedical.onlineShopping.order.dto.OrderStatus;
import com.shrirammedical.onlineShopping.order.entity.Order;
import com.shrirammedical.onlineShopping.order.entity.OrderItems;
import com.shrirammedical.onlineShopping.order.repository.OrderRepo;
import com.shrirammedical.onlineShopping.order.service.OrderService;
import com.shrirammedical.onlineShopping.payment.dto.PaymentStatus;
import com.shrirammedical.onlineShopping.product.entity.Products;
import com.shrirammedical.onlineShopping.product.repository.ProductRepo;
import com.shrirammedical.onlineShopping.user.entity.Address;
import com.shrirammedical.onlineShopping.user.repository.AddressRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;
    private final ApplicationEventPublisher eventPublisher;


    /**
     * @param order
     * @return
     */
    @Override
    public Order createOrder(OrderRequestDto request) {
        String userId = currentUser();
        Double totalAmount = 0d;
        Order order = new Order();
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<Long> productIds = cart.getItems().stream()
                .map(item -> item.getProductId())
                .toList();

        Map<Long, Products> productMap = productRepo.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Products::getProductId, p -> p));

        List<OrderItems> orderItems = cart.getItems().stream()
                .map(item -> {

                    Products product = productMap.get(item.getProductId());

                    if (product == null) {
                        throw new RuntimeException("Product not found: " + item.getProductId());
                    }

                    OrderItems orderItem = new OrderItems();
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setProductName(product.getProductName());
                    orderItem.setProductId(product.getProductId());
                    orderItem.setProductDescription(product.getDescription());
                    orderItem.setProductPrice(product.getRate());
                    orderItem.setTotalPrice(product.getRate() * item.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList();

        order.setItems(orderItems);
        order.setUserId(userId);
        if ("COD".equals(request.getPaymentMethod())) {
            order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT); // paid on delivery
            order.setOrderStatus(OrderStatus.CONFIRMED);    // order confirmed immediately
        } else {
            order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
            order.setOrderStatus(OrderStatus.PAYMENT_PENDING);
        }
        order.setPaymentMethod(request.getPaymentMethod());
        order.setAddress(formatAddress(request.getAddressId()));
        order.setTotalAmount(calculateTotalAmount(orderItems));

        // Save order
        Order savedOrder = orderRepo.save(order);
        log.info("Order created with id: {}", savedOrder.getOrderId());

        // Empty the cart
        if(Objects.equals(order.getPaymentMethod(), "COD")){
            doEmptyCart(userId);
        }


        // Kafka message for Notification
        publishOrderPlacedEvent(order);

        return savedOrder;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<Order> getAllOrders() {
        String userId = currentUser();
        return orderRepo.findOrderByUserId(userId);

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    /**
     * @param orderId
     */
    @Override
    public void markAsConfirmed(Long orderId) {
        int updated = orderRepo.updateOrderStatus(orderId, "CONFIRMED");
        if (updated > 0) {
            log.info("Order confirmed with id: {}", orderId);
        } else {
            throw new RuntimeException("Error while confirming order status for id: " + orderId);
        }
    }

    // Private Helper Methods

    // get userid from JWT
    private String currentUser(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    // format address in {addressLine1, addressLine2, city, pincode}
    private String formatAddress(Long addressId){
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address Not Found"));

        return address.getAddressLine1() + ", " + address.getAddressLine2()
                + ", " + address.getCity() + ", " + address.getPinCode();
    }

    // Calculate total order amount
    private Double calculateTotalAmount(List<OrderItems> orderItems) {
        Double totalAmount = 0d;
        for (OrderItems orderItem : orderItems) {
            totalAmount += orderItem.getTotalPrice();
        }
        return totalAmount;
    }

    // Delete everything from cart after order placed
    private void doEmptyCart(String userId){
        cartRepo.deleteByUserId(userId);
    }

    private void publishOrderPlacedEvent(Order order) {

        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setOrderId(order.getOrderId());
        event.setUserId(order.getUserId());
        event.setTotalAmount(order.getTotalAmount());

        List<OrderItemEvent> items = order.getItems().stream()
                .map(i -> {
                    OrderItemEvent e = new OrderItemEvent();
                    e.setProductId(i.getProductId());
                    e.setQuantity(i.getQuantity());
                    return e;
                })
                .toList();

        event.setItems(items);

        eventPublisher.publishEvent(event); // 🔥 for now
    }
}
