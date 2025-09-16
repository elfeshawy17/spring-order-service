package org.ecommercapp.ecommerce.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.ecommercapp.ecommerce.order.dto.order.OrderRequestDto;
import org.ecommercapp.ecommerce.order.dto.order.OrderResponseDto;
import org.ecommercapp.ecommerce.order.dto.orderItem.OrderItemRequestDto;
import org.ecommercapp.ecommerce.order.entity.Order;
import org.ecommercapp.ecommerce.order.entity.OrderItem;
import org.ecommercapp.ecommerce.order.mapper.OrderMapper;
import org.ecommercapp.ecommerce.order.repo.OrderRepo;
import org.ecommercapp.ecommerce.order.service.OrderService;
import org.ecommercapp.ecommerce.product.entity.Product;
import org.ecommercapp.ecommerce.product.repo.ProductRepo;
import org.ecommercapp.ecommerce.product.service.ProductService;
import org.ecommercapp.ecommerce.shared.exception.RecordNotFoundException;
import org.ecommercapp.ecommerce.shared.util.CodeGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper;
    private final ProductRepo productRepo;
    private final ProductService productService;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto dto) {
        // Step 1: Convert the order request dto to an entity and set initial order data
        Order order = orderMapper.toEntity(dto);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("CREATED");
        order.setCode(CodeGenerator.generateOrderCode());

        // Step 2: Create a list of order items and associate them with the order
        List<OrderItem> orderItems = createOrderItemsList(dto.getOrderItems(), order);
        order.setOrderItems(orderItems);

        // Step 3: Calculate the total price for the order
        order.setTotalPrice(calculateTotalPrice(orderItems));

        // Step 4: Save the order to the repository
        Order saved = orderRepo.save(order);

        // Step 5: Return the order response dto
        return orderMapper.toResponseDto(saved);
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItemsList(List<OrderItemRequestDto> orderItems, Order order) {
        return orderItems.stream()
                .map(item -> {
                    Product product = getProduct(item);

                    productService.deductStock(product.getId(), item.getQuantity());

                    return OrderItem.builder()
                            .product(product)
                            .quantity(item.getQuantity())
                            .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .code(CodeGenerator.generateOrderItemCode())
                            .order(order)
                            .build();
                })
                .toList();
    }

    private Product getProduct(OrderItemRequestDto item) {
        return productRepo.findById(item.getProductId())
                .orElseThrow(() -> new RecordNotFoundException("Product", item.getProductId()));
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepo.findAll()
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

}
