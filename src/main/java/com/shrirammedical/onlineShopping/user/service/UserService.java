package com.shrirammedical.onlineShopping.user.service;

import com.shrirammedical.onlineShopping.user.dto.AdminUserUpdateRequest;
import com.shrirammedical.onlineShopping.user.dto.AuthRequest;
import com.shrirammedical.onlineShopping.user.dto.UserProfile;
import com.shrirammedical.onlineShopping.user.dto.UserUpdateRequest;
import com.shrirammedical.onlineShopping.user.entity.User;

public interface UserService {

    User register(User user);

    String login(AuthRequest request);

    User updateSelf(UserUpdateRequest request);

    User updateByAdmin(String userId, AdminUserUpdateRequest request);

    UserProfile getUserById(String userId);

    Boolean isLoggedIn();
}
