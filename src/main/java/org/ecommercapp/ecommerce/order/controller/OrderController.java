package org.ecommercapp.ecommerce.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.order.dto.order.OrderRequestDto;
import org.ecommercapp.ecommerce.order.dto.order.OrderResponseDto;
import org.ecommercapp.ecommerce.order.service.OrderService;
import org.ecommercapp.ecommerce.shared.dto.ApiResponse;
import org.ecommercapp.ecommerce.shared.util.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final ResponseBuilder responseBuilder;

    @PostMapping("/place")
    public ResponseEntity<ApiResponse<OrderResponseDto>> placeOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto order = orderService.placeOrder(orderRequestDto);
        return responseBuilder.created(order);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllProducts() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return responseBuilder.ok(orders);
    }

}
