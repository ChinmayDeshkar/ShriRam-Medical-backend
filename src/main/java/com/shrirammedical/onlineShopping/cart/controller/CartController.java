package com.shrirammedical.onlineShopping.cart.controller;

import com.shrirammedical.onlineShopping.cart.dto.AddToCartRequest;
import com.shrirammedical.onlineShopping.cart.dto.CartResponse;
import com.shrirammedical.onlineShopping.cart.dto.UpdateCartItemRequest;
import com.shrirammedical.onlineShopping.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request){
        cartService.addToCart(request);
        return ResponseEntity.ok("added");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartItemRequest request){
        try {
            cartService.updateCartItem(request);
            return ResponseEntity.ok("Updated");
        } catch (RuntimeException re){
            log.error("Error occured while updating cart item, error = " + re);
            re.printStackTrace();
            return ResponseEntity.internalServerError().body("Item not updated");
        }
    }

}
