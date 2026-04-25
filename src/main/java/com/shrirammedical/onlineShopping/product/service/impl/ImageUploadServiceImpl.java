package com.shrirammedical.onlineShopping.product.service.impl;

import com.cloudinary.Cloudinary;
import com.shrirammedical.onlineShopping.product.service.ImageUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    private final Cloudinary cloudinary;

    /**
     * @param images
     * @return
     */
    @Override
    public List<String> uploadImage(List<MultipartFile> images) {
        log.info("Uploading image");
        if(images.size() > 7){
            throw new RuntimeException("More than 7 images selected");
        }


        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            try{
                log.info("Uploading image: {}", image.getOriginalFilename());
                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        Map.of("folder", "products")
                );

                String imageUrl = uploadResult.get("secure_url").toString();
                imageUrls.add(imageUrl);
                log.info("Image url: {}", imageUrl);
            } catch (Exception e){
                throw new RuntimeException("Error while uploading image " + image.getOriginalFilename(), e);
            }
        }
        return imageUrls;
    }
}
