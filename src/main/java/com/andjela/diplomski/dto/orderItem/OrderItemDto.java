package com.andjela.diplomski.dto.orderItem;

import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private OrderDto order;
    private ProductDto productDto;
    private Integer price;
    private Integer discountedPrice;
    private LocalDateTime deliveryDate;
    private int quantity;
    private Long userId;
}
