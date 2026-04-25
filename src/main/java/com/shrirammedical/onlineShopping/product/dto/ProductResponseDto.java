package com.shrirammedical.onlineShopping.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private String shortDescription;
    private String description;
    private Double rate;
    private Long stock;
    private Long productType;
    LocalDate expiryDate;
    LocalDate manufacturingDate;
    String batchNumber;
    Boolean isPrescriptionRequired;
    private Boolean active;
    private String createdBy;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String updatedBy;
    private  LocalDateTime updatedDate;
    private List<String> imageUrls;

}
