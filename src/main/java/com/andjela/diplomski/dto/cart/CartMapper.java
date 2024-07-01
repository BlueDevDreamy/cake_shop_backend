package com.andjela.diplomski.dto.cart;

import com.andjela.diplomski.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {
    CartMapper MAPPER = Mappers.getMapper(CartMapper.class);

    //    @Mapping(source = "cartItems", target = "cartItems", ignore = true)
//    @Mapping(target = "cartItems", ignore = true)

//    @Mapping(target = "cartItems", source = "cartItems")

//    @Mapping(target = "cartItems", source = "cartItems")
    @Mapping(source = "user.id", target = "userId")
    CartDto mapToCartDto(Cart cart);

//    @Mapping(target = "cartItems", source = "cartItems")
    Cart mapToCart(CartDto cartDto);

}
