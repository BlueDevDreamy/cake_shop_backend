package com.andjela.diplomski.dto.orderItem;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.entity.OrderItemFlavorTier;
import com.andjela.diplomski.entity.codebook.Flavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
//    private OrderDto order;
//    private CakeDto cakeDto;
//    private Integer price;
//    private Integer discountedPrice;
//    private LocalDateTime deliveryDate;
//    private int quantity;
//    private Long userId;

//    private double selectedWeight;
//    private int selectedTiers;
//    private int piecesNumber;
//    private int totalPrice;
//    private Long cakeId;
//    private List<Flavor> flavors;
//    private String note;
//    private int fakeTier;
//    private Order order;
//    private Long userId;

    private double selectedWeight;
    private int selectedTiers;
    private int piecesNumber;
    private int totalPrice;
    private Long cakeId;
    private List<OrderItemFlavorTier> flavors;
    private String note;
    private int fakeTier;
    private Long userId;
}
