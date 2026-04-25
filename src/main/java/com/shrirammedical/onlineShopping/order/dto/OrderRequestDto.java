package com.shrirammedical.onlineShopping.order.dto;

import com.shrirammedical.onlineShopping.order.entity.OrderItems;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    Long addressId;
    String paymentMethod;
    String clientRequestId;


}
