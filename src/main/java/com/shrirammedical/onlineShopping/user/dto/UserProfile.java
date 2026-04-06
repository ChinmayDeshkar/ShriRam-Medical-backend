package com.shrirammedical.onlineShopping.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfile {

    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
}
