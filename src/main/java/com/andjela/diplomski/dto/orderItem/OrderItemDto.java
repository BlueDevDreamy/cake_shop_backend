package com.andjela.diplomski.dto.orderItem;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private OrderDto order;
    private CakeDto cakeDto;
    private Integer price;
    private Integer discountedPrice;
    private LocalDateTime deliveryDate;
    private int quantity;
    private Long userId;
}
