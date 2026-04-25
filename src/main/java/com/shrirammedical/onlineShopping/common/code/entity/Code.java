package com.shrirammedical.onlineShopping.common.code.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_code")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "num_code")
    Long codeId;
    @Column(name = "num_codetype", nullable = false)
    Long codeType;
    @Column(name = "Cde_code", nullable = false)
    String code;
    @Column(name = "txt_shortdesc",nullable = false)
    String shortDesc;
    @Column(name = "txt_desc", nullable = false)
    String desc;

}
