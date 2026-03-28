package com.shrirammedical.onlineShopping.user.service.impl;

import com.shrirammedical.onlineShopping.common.Role;
import com.shrirammedical.onlineShopping.config.JwtUtil;
import com.shrirammedical.onlineShopping.user.dto.AuthRequest;
import com.shrirammedical.onlineShopping.user.entity.User;
import com.shrirammedical.onlineShopping.user.repository.UserRepo;
import com.shrirammedical.onlineShopping.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER); // default
        userRepository.save(user);
    }

    public String login(AuthRequest request) {
        log.debug("Login request for emailId: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email Address"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
