package com.andjela.diplomski.dto.order;

import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.orderItem.OrderItemDto;
import com.andjela.diplomski.entity.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    //    private String orderId;
//    private Long userId;
//    private List<OrderItemDto> orderItems;
//    private LocalDateTime orderDate;
//    private LocalDateTime deliveryDate;
//    private AddressDto addressDto;
////    private PaymentDetails paymentDetails; //mozda ne treba da bude vidljivo u dto
//    private Integer totalPrice;
//    private Integer totalDiscountedPrice;
//    private Integer discount;
//    private String orderStatus;
//    private int totalItem;

    private Long userId;
    private List<Long> cartItems;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private AddressDto addressDto;
    //    private PaymentDetails paymentDetails; //mozda ne treba da bude vidljivo u dto
    private Integer totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private int totalItem;
}
