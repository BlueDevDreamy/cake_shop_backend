package com.andjela.diplomski.dto.cart;

import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private UserDto user;
    private Set<CartItemDto> cartItems;
    private Integer totalPrice;
    private int totalItem;
    private Integer totalDiscountedPrice;
    private Integer discount;
}
