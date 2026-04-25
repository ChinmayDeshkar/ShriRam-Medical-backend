package com.shrirammedical.onlineShopping.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploadService {

    List<String> uploadImage(List<MultipartFile> images);
}
