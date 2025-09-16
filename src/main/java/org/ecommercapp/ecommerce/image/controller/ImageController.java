package org.ecommercapp.ecommerce.image.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.image.dto.ImageUploadResponse;
import org.ecommercapp.ecommerce.image.service.ImageService;
import org.ecommercapp.ecommerce.shared.dto.ApiResponse;
import org.ecommercapp.ecommerce.shared.util.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;
    private final ResponseBuilder responseBuilder;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadImage(@RequestParam("image") @NotNull MultipartFile image) throws IOException {
            ImageUploadResponse response = imageService.uploadImage(image);
            return responseBuilder.ok(response);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String publicId) throws IOException {
            imageService.deleteImage(publicId);
            return responseBuilder.noContent();
    }

}
