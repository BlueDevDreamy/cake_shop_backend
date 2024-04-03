package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartItemMapper {

    CartItemMapper MAPPER = Mappers.getMapper(CartItemMapper.class);

    CartItemDto mapToCartItemDto(CartItem cartItem);
    CartItem mapToCartItem(CartItemDto cartItemDto);
}
