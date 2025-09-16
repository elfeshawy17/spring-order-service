package org.ecommercapp.ecommerce.image.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.image.dto.ImageUploadResponse;
import org.ecommercapp.ecommerce.image.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );

    public ImageUploadResponse uploadImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) throw new IllegalArgumentException("File is empty");

        if (!ALLOWED_TYPES.contains(image.getContentType())) {
            throw new IllegalArgumentException("Unsupported file type: " + image.getContentType() +
                    ". Allowed types: " + ALLOWED_TYPES);
        }

        Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        return new ImageUploadResponse(
                uploadResult.get("secure_url").toString(),
                uploadResult.get("public_id").toString(),
                uploadResult.get("format").toString(),
                Long.parseLong(uploadResult.get("bytes").toString())
        );
    }

    public void deleteImage(String publicId) throws IOException {
        if (publicId == null || publicId.trim().isEmpty()) {
            throw new IllegalArgumentException("Public ID cannot be null or empty");
        }

        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}
