package org.ecommercapp.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private String category;

    @Column(name = "releaseDate")
    private LocalDateTime releaseDate;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "imagePublicId")
    private String imagePublicId;

    @Version
    @Column(name = "version")
    private Long version;

}
