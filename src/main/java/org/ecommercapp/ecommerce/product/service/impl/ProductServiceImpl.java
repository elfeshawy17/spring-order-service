package org.ecommercapp.ecommerce.product.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.image.dto.ImageUploadResponse;
import org.ecommercapp.ecommerce.image.service.ImageService;
import org.ecommercapp.ecommerce.product.dto.ProductCreateDto;
import org.ecommercapp.ecommerce.product.dto.ProductResponseDto;
import org.ecommercapp.ecommerce.product.dto.ProductUpdateDto;
import org.ecommercapp.ecommerce.product.entity.Product;
import org.ecommercapp.ecommerce.product.mapper.ProductMapper;
import org.ecommercapp.ecommerce.product.repo.ProductRepo;
import org.ecommercapp.ecommerce.product.service.ProductService;
import org.ecommercapp.ecommerce.shared.exception.DuplicateResourceException;
import org.ecommercapp.ecommerce.shared.exception.RecordNotFoundException;
import org.ecommercapp.ecommerce.shared.exception.StockDeductionException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private static final Integer MAX_RETRIES = 3;

    @Override
    public ProductResponseDto add(ProductCreateDto dto, MultipartFile image) throws IOException {
        if (productRepo.existsByNameAndBrandAndCategory(
                dto.getName(),
                dto.getBrand(),
                dto.getCategory()
        )){
            throw new DuplicateResourceException("Product already exists");
        }

        Product product = productMapper.toEntity(dto);
        product.setAvailable(product.getStock() > 0);

        if (image!= null && !image.isEmpty()) {
            ImageUploadResponse uploaded = imageService.uploadImage(image);
            product.setImageUrl(uploaded.getUrl());
            product.setImagePublicId(uploaded.getPublicId());
        }

        return productMapper.toResponseDto(productRepo.save(product));
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new RecordNotFoundException("Product", id));
    }

    @Override
    public ProductResponseDto update(Long productId, ProductUpdateDto dto, MultipartFile image) throws IOException {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new RecordNotFoundException("Product", productId));

        productMapper.updateProductFromDro(dto, existingProduct);

        if (image!= null && !image.isEmpty()) {
            ImageUploadResponse response = imageService.uploadImage(image);
            existingProduct.setImageUrl(response.getUrl());
        }

        productRepo.save(existingProduct);

        return productMapper.toResponseDto(existingProduct);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", id));
        productRepo.delete(product);
    }

    @Override
    public List<ProductResponseDto> search(String keyword) {
        return productRepo.searchProducts(keyword)
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deductStock(Long productId, Long quantity) {
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < MAX_RETRIES) {
            attempts++;

            try {
                Product product = productRepo.findById(productId)
                        .orElseThrow(() -> new RecordNotFoundException("Product", productId));

                if (product.getStock() < quantity) {
                    throw new StockDeductionException("Not enough stock available for product: " + product.getName());
                }

                product.setStock(product.getStock() - quantity);
                product.setAvailable(product.getStock() > 0);

                productRepo.save(product);
                success = true;

            } catch (ObjectOptimisticLockingFailureException ex) {
                if (attempts >= MAX_RETRIES) {
                    throw new StockDeductionException(
                            "Could not deduct stock for productId " + productId +
                                    " due to concurrent updates. Please try again."
                    );
                }
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        }
    }

}
