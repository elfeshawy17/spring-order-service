package org.ecommercapp.ecommerce.order.service;

import org.ecommercapp.ecommerce.order.dto.order.OrderRequestDto;
import org.ecommercapp.ecommerce.order.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getAllOrders();

}
