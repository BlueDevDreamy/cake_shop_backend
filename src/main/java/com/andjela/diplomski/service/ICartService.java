package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cartItem.CartItemCreateDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.user.UserDto;


public interface ICartService {
    CartDto createCart(UserDto userDto);

    CartItemDto addCartItem(Long userId, CartItemCreateDto req);

    CartDto getUserCart(Long userId);
}
