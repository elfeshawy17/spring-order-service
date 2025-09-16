package org.ecommercapp.ecommerce.order.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ecommercapp.ecommerce.order.dto.orderItem.OrderItemResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private String status;
    private String code;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> orderItems;
}
