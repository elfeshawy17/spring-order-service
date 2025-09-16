package org.ecommercapp.ecommerce.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {
    private String url;
    private String publicId;
    private String format;
    private Long bytes;
}
