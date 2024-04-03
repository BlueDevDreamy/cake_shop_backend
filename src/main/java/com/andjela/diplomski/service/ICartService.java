package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.request.AddItemRequest;

public interface ICartService {
    CartDto createCart(UserDto userDto);
    String addCartItem(Long userId, AddItemRequest req);
    CartDto getUserCart(Long userId);
}
