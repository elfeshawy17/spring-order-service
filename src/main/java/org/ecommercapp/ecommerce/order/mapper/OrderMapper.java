package org.ecommercapp.ecommerce.order.mapper;

import org.ecommercapp.ecommerce.order.dto.order.OrderRequestDto;
import org.ecommercapp.ecommerce.order.dto.order.OrderResponseDto;
import org.ecommercapp.ecommerce.order.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    Order toEntity(OrderRequestDto dto);

    @Mapping(source = "id", target = "orderId")
    OrderResponseDto toResponseDto(Order entity);

}
