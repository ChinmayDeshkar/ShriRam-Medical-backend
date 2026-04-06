package com.shrirammedical.onlineShopping.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "num_producttype")
    private Long productType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate expiryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate manufacturingDate;
    String batchNumber;
    Boolean isPrescriptionRequired;
    private Boolean active;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate = LocalDateTime.now();
    private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private  LocalDateTime updatedDate;

}
