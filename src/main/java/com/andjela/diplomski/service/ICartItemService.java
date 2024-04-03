package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.product.ProductDto;

public interface ICartItemService {
    CartItemDto createCartItem(CartItemDto cartItemDto);
    CartItemDto updateCartItem(Long userId, Long id, CartItemDto cartItemDto);

    CartItemDto isCartItemExists(CartDto cartDto, ProductDto productDto, String weight, Long userId);

    String removeCartItem(Long userId, Long cartItemId);

    CartItemDto findCartItemById(Long id);

}
