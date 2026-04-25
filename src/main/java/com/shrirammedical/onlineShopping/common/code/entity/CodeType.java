package com.shrirammedical.onlineShopping.common.code.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_codetype")
@Entity
@Getter
@Setter
public class CodeType {
    // Coming in v3

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num_codetype")
    Long codeTypeId;
    @Column(name = "txt_codetype", nullable = false, unique = true)
    String codeType;
    @Column(name = "txt_description")
    String description;
}
