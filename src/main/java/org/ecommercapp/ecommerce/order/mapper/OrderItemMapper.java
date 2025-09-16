package org.ecommercapp.ecommerce.order.mapper;

import org.ecommercapp.ecommerce.order.dto.orderItem.OrderItemResponseDto;
import org.ecommercapp.ecommerce.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDto toOrderItemResponseDto(OrderItem entity);

}
