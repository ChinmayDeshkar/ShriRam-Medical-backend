package com.shrirammedical.onlineShopping.config;


import com.shrirammedical.onlineShopping.common.role.RoleService;
import com.shrirammedical.onlineShopping.user.entity.User;
import com.shrirammedical.onlineShopping.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("User is: " +user);
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + roleService.getRoleNameById(user.getRoleId())))
        );
    }
}