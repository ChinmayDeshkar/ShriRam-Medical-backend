package com.shrirammedical.onlineShopping.user.controller;

import com.shrirammedical.onlineShopping.user.dto.AdminUserUpdateRequest;
import com.shrirammedical.onlineShopping.user.dto.AuthRequest;
import com.shrirammedical.onlineShopping.user.dto.AuthResponse;
import com.shrirammedical.onlineShopping.user.dto.UserUpdateRequest;
import com.shrirammedical.onlineShopping.user.entity.User;
import com.shrirammedical.onlineShopping.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        User createadUser = userService.register(user);
        return ResponseEntity.ok("User registered successfully, userid= " + createadUser.getUserId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            String token = userService.login(request);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials");
        }
    }

    @PutMapping("/users/me")
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE','ADMIN')")
    public ResponseEntity<?> updateMyProfile(@RequestBody UserUpdateRequest request){

        try {
            userService.updateSelf(request);
            return ResponseEntity.ok(Map.of("Message", "Profile updated"));
        } catch (Exception e) {
            log.error("Error while updating user");
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable String id,
                                               @RequestBody AdminUserUpdateRequest request){

        try{
            userService.updateByAdmin(id, request);
            return ResponseEntity.ok(Map.of("Message", "User profile updated"));
        } catch (Exception e) {
            log.error("Error while updating user whith id = " + id);
            throw new RuntimeException(e);
        }
    }
}