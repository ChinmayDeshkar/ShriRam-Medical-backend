package com.shrirammedical.onlineShopping.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shrirammedical.onlineShopping.common.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String userId;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    @Column(name = "num_role", nullable = false)
    private Long roleId;
    private boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate = LocalDateTime.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedDate;
}

