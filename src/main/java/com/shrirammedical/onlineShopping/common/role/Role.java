package com.shrirammedical.onlineShopping.common.role;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "tb_role")
@Entity
@Getter
public class Role {

    public final static String admin = "ADMIN";
    public final static String customer = "CUSTOMER";
    public final static String employee = "EMPLOYEE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num_role")
    private Long roleId;
    @Column(name = "cde_role", nullable = false, unique = true)
    private String roleName;
    @Column(name = "txt_desc")
    private String roleDescription;
}
