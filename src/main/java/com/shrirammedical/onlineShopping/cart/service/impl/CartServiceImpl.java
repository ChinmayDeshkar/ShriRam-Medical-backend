package com.shrirammedical.onlineShopping.cart.service.impl;

import com.shrirammedical.onlineShopping.cart.dto.AddToCartRequest;
import com.shrirammedical.onlineShopping.cart.dto.CartItemsDto;
import com.shrirammedical.onlineShopping.cart.dto.CartResponse;
import com.shrirammedical.onlineShopping.cart.dto.UpdateCartItemRequest;
import com.shrirammedical.onlineShopping.cart.entity.Cart;
import com.shrirammedical.onlineShopping.cart.entity.CartItem;
import com.shrirammedical.onlineShopping.cart.repository.CartItemRepo;
import com.shrirammedical.onlineShopping.cart.repository.CartRepo;
import com.shrirammedical.onlineShopping.cart.service.CartService;
import com.shrirammedical.onlineShopping.product.entity.Products;
import com.shrirammedical.onlineShopping.product.repository.ProductRepo;
import com.shrirammedical.onlineShopping.user.entity.User;
import com.shrirammedical.onlineShopping.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public Cart addToCart(AddToCartRequest request) {
        String userId = getCurrentUser();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Cart cart = getCartOrCreate(userId);

        Products product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Not found"));

        // Check if already exists
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductId(product.getProductId());
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getRate()); // snapshot

            cart.getItems().add(item);
        }

        return cartRepo.save(cart);

    }

    @Override
    public CartResponse getCart() {
        String userId = getCurrentUser();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUserId(userId)
                .orElse(null);

        if (cart == null){
            log.info("Cart is empty");
            return null;
        }

        List<CartItemsDto> items = cart.getItems().stream()
                .sorted(Comparator.comparing(CartItem::getAddedOn).reversed())
                .map(item -> new CartItemsDto(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        return new CartResponse(items, total);
    }

    @Override
    @Transactional
    public void updateCartItem(UpdateCartItemRequest request) {
        Cart cart = cartRepo.findByUserId(getCurrentUser())
                .orElseThrow(() -> new RuntimeException("No active cart for user: " + getCurrentUser()));

        CartItem cartItem = cartItemRepo.findByProductIdAndCart(request.getItemId(), cart)
                .orElseThrow(() -> new RuntimeException("Item not Found"));

        if(request.getQuantity() < 0) {
            throw new RuntimeException("Quantity getting negative");
        }

        if(request.getQuantity() == 0){
            // remove product
            removeItem(request.getItemId(), cart);
        }
        else{
            // update quantity
            updateItemQuantity(request.getQuantity(), cartItem);
        }
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public int getCartQuantityByProductAndUserId(Long productId) {
        CartResponse cartResponse = getCart();
        if(cartResponse == null) {
            return 0;
        }
        int quantity = cartResponse.getItems().stream()
                .filter(item -> Objects.equals(item.getProductId(), productId))
                .mapToInt(CartItemsDto::getQuantity)
                .findFirst()
                .orElse(0);
        log.info("Quantity: {}", quantity);
        return quantity;
    }

    /**
     * @param productId
     */
    @Override
    @Transactional
    public void deleteCartItem(Long productId) {
        Cart cart = getCartOrCreate(getCurrentUser());
        if(cart == null) {
            return;
        }
        removeItem(productId, cart);
    }

    // Helpers Methods

    private String getCurrentUser(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    private Cart getCartOrCreate(String userId){
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cartRepo.save(cart);
                });
    }


    private void removeItem(Long itemId, Cart cart){
        log.debug("Removing item: {}", itemId);
        cartItemRepo.deleteByProductIdAndCart(itemId, cart);
    }

    private void updateItemQuantity(Integer quantity, CartItem cartItem){
        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
    }
}
