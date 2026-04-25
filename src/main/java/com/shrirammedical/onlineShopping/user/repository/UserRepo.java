package com.shrirammedical.onlineShopping.user.repository;

import com.shrirammedical.onlineShopping.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String emailId);
    Boolean existsByEmail(String emailId);
    Boolean existsByPhoneNumber(String phoneNumber);
}
