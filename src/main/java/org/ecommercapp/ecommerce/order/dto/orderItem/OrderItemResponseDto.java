package org.ecommercapp.ecommerce.order.dto.orderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
    private String productName;
    private String code;
    private Long quantity;
    private BigDecimal totalPrice;
}
