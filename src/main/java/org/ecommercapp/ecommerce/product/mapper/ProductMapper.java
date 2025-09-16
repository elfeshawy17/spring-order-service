package org.ecommercapp.ecommerce.product.mapper;

import org.ecommercapp.ecommerce.product.dto.ProductCreateDto;
import org.ecommercapp.ecommerce.product.dto.ProductResponseDto;
import org.ecommercapp.ecommerce.product.dto.ProductUpdateDto;
import org.ecommercapp.ecommerce.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductCreateDto dto);

    ProductResponseDto toResponseDto(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "releaseDate", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateProductFromDro(ProductUpdateDto dto, @MappingTarget Product entity);

}
