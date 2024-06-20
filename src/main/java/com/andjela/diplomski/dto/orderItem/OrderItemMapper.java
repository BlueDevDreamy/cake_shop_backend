package com.andjela.diplomski.dto.orderItem;

import com.andjela.diplomski.dto.cartItemFlavorTier.CartItemFlavorTierDto;
import com.andjela.diplomski.dto.orderItemFlavorTier.OrderItemFlavorTierDto;
import com.andjela.diplomski.entity.CartItemFlavorTier;
import com.andjela.diplomski.entity.OrderItem;
import com.andjela.diplomski.entity.OrderItemFlavorTier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper MAPPER = Mappers.getMapper(OrderItemMapper.class);

//    @Mapping(source = "cake.id", target = "cakeId")
//    @Mapping(source = "cake.title", target = "cakeTitle")
//    @Mapping(source = "cake.imageUrl", target = "cakeImageUrl")
//    @Mapping(target = "flavors", source = "orderItemFlavorTiers")
    OrderItemDto mapToOrderItemDto(OrderItem orderItem);

    OrderItem mapToOrderItem(OrderItemDto orderItemDto);

}
