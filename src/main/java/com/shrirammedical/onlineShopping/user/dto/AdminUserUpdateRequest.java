package com.shrirammedical.onlineShopping.user.dto;

import com.shrirammedical.onlineShopping.common.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserUpdateRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
    private Boolean active;

}
