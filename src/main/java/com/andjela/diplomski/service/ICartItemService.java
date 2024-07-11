package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;

public interface ICartItemService {
    CartItemDto createCartItem(CartItemDto cartItemDto);

    CartItemDto updateCartItem(Long userId, Long id, CartItemDto cartItemDto);

    CartItemDto isCartItemExists(CartDto cartDto, CakeDto cakeDto, Long userId);

    String removeCartItem(Long userId, Long cartItemId);

    CartItemDto findCartItemById(Long id);

}
