package org.ecommercapp.ecommerce.image.service;

import org.ecommercapp.ecommerce.image.dto.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageUploadResponse uploadImage(MultipartFile image) throws IOException;
    void deleteImage(String publicId) throws IOException;
}
