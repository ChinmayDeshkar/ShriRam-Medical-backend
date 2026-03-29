package com.shrirammedical.onlineShopping.user.service.impl;

import com.shrirammedical.onlineShopping.common.Role;
import com.shrirammedical.onlineShopping.config.JwtUtil;
import com.shrirammedical.onlineShopping.user.dto.AdminUserUpdateRequest;
import com.shrirammedical.onlineShopping.user.dto.AuthRequest;
import com.shrirammedical.onlineShopping.user.dto.UserUpdateRequest;
import com.shrirammedical.onlineShopping.user.entity.User;
import com.shrirammedical.onlineShopping.user.repository.UserRepo;
import com.shrirammedical.onlineShopping.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER); // default
        user.setActive(true);
        long count = userRepository.count() + 1;
        log.info(String.valueOf(count));
        user.setUserId("" + count);
        return userRepository.save(user);
    }

    public String login(AuthRequest request) {
        log.debug("Login request for userid: {}", request.getUsername());
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid User name"));

        if(!user.isActive()){
            log.error("Error while login" + user.getUserId());
            throw new RuntimeException("User is inactive, userId = "+ user.getUserId());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUserId());
    }

    @Override
    public User updateSelf(UserUpdateRequest request) {

        String userId = JwtUtil.getCurrentUser();
        log.info("Updating self user details for userId = " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(request.getEmail() != null){
            user.setEmail(request.getEmail());
        }

        if(request.getName() != null){
            user.setName(request.getName());
        }

        if(request.getPhoneNumber() != null){
            user.setPhoneNumber(request.getPhoneNumber());
        }

        user.setUpdatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User updateByAdmin(String userId, AdminUserUpdateRequest request) {
        String adminUserId = JwtUtil.getCurrentUser();

        log.info("Updating user details by {} for userId = {}", userId, adminUserId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(request.getActive() != null){
            user.setActive(request.getActive());
        }
        if (request.getName() != null){
            user.setName(request.getName());
        }
        if(request.getEmail() != null){
            user.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null){
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getRole() != null){
            user.setRole(request.getRole());
        }

        user.setUpdatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }
}
