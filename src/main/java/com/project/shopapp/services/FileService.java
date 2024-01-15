package com.project.shopapp.services;

import com.project.shopapp.controllers.ProductController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public interface FileService {

    String storeFile(MultipartFile file);

    static String getImageUrl(String imageName) {
        return MvcUriComponentsBuilder.fromMethodName(ProductController.class, "getImage", imageName).build().toUriString();

    }
}
