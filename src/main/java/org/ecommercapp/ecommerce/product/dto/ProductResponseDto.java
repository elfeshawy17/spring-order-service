package org.ecommercapp.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private Double price;
    private String category;
    private LocalDateTime releaseDate;
    private Boolean available;
    private Integer stock;
    private String imageUrl;

}
