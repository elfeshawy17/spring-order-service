package org.ecommercapp.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.product.dto.ProductCreateDto;
import org.ecommercapp.ecommerce.product.dto.ProductResponseDto;
import org.ecommercapp.ecommerce.product.dto.ProductUpdateDto;
import org.ecommercapp.ecommerce.product.service.ProductService;
import org.ecommercapp.ecommerce.shared.dto.ApiResponse;
import org.ecommercapp.ecommerce.shared.util.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final ResponseBuilder responseBuilder;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @RequestPart("product") ProductCreateDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ProductResponseDto productResponseDto = productService.add(dto, image);
        return responseBuilder.created(productResponseDto);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productService.findAll();
        return responseBuilder.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.findById(productId);
        return responseBuilder.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> searchProducts(
            @RequestParam("keyword") String keyword
    ) {
        List<ProductResponseDto> products = productService.search(keyword);
        return responseBuilder.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @RequestPart("product") ProductUpdateDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long productId
            ) throws IOException {
        ProductResponseDto product = productService.update(productId, dto, image);
        return responseBuilder.ok(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return responseBuilder.noContent();
    }

}
