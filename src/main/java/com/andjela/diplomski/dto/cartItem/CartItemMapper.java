package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartItemMapper {

    CartItemMapper MAPPER = Mappers.getMapper(CartItemMapper.class);

    //    @Mapping(target = "cart", ignore = true)

//    @Mapping(target = "cakeId", source = "cake.id")
    CartItemDto mapToCartItemDto(CartItem cartItem);

//    @Mapping(target = "cake.id", source = "cakeId")
//    @Mapping(target = "cart", ignore = true)
    CartItem mapToCartItem(CartItemDto cartItemDto);
}
