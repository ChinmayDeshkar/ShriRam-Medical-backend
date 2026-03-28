package com.shrirammedical.onlineShopping.user.service;

import com.shrirammedical.onlineShopping.user.dto.AuthRequest;
import com.shrirammedical.onlineShopping.user.entity.User;

public interface UserService {

    void register(User user);

    String login(AuthRequest request);
}
