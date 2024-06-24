package com.andjela.diplomski.dto.order;

import com.andjela.diplomski.dto.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    private Long userId;
    private List<Long> cartItems;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private AddressDto addressDto;
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItem;
}
