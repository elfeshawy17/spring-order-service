package org.ecommercapp.ecommerce.product.service;

import org.ecommercapp.ecommerce.product.dto.ProductCreateDto;
import org.ecommercapp.ecommerce.product.dto.ProductResponseDto;
import org.ecommercapp.ecommerce.product.dto.ProductUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductResponseDto add(ProductCreateDto dto, MultipartFile image) throws IOException;
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(Long id);
    ProductResponseDto update(Long productId, ProductUpdateDto dto, MultipartFile image) throws IOException;
    void delete(Long id);
    List<ProductResponseDto> search(String keyword);
    void deductStock(Long productId, Long quantity);

}
