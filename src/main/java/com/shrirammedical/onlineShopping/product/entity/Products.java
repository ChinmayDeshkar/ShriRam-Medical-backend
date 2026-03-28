package com.shrirammedical.onlineShopping.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tb_products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long productId;
    private String productName;
    private String shortDescription;
    private String description;
    private Double rate;
    private Long stock;
    private Long categoryId;
    private Boolean active;

}
