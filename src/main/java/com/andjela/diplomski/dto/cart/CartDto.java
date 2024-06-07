package com.andjela.diplomski.dto.cart;

import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.entity.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    private UserDto user;
    private List<CartItem> cartItems;
    private Integer totalPrice;
    private int totalItem;
    private Integer totalDiscountedPrice;
    private Integer discount;
}
