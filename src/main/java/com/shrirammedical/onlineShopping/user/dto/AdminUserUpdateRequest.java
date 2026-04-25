package com.shrirammedical.onlineShopping.user.dto;

import com.shrirammedical.onlineShopping.common.role.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserUpdateRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private Boolean active;

}
