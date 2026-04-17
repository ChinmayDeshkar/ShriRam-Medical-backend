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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartResponse getCart(){
        cartService.getCartQuantityByProductAndUserId(15L);
        return cartService.getCart();
    }

    @GetMapping("/product-in-cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public int getProductInCart(@RequestParam Long productId){
        return cartService.getCartQuantityByProductAndUserId(productId);
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request){
        cartService.addToCart(request);
        return ResponseEntity.ok(Map.of("message", "success"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartItemRequest request){
        try {
            cartService.updateCartItem(request);
            return ResponseEntity.ok(Map.of("message", "updated"));
        } catch (RuntimeException re){
            log.error("Error occured while updating cart item, error = " + re);
            re.printStackTrace();
            return ResponseEntity.internalServerError().body("Item not updated");
        }
    }

    @DeleteMapping("/remove-product")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> removeProductFromCart(@RequestParam Long productId){
        cartService.deleteCartItem(productId);
        return ResponseEntity.ok(Map.of("message", "success"));
    }
}
