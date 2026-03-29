package com.shrirammedical.onlineShopping.user.dto;

import com.shrirammedical.onlineShopping.common.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    private String name;
    private String email;
    private String phoneNumber;
}
