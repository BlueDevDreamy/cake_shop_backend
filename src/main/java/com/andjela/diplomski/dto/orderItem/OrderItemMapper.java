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

    OrderItemDto mapToOrderItemDto(OrderItem orderItem);
    OrderItem mapToOrderItem(OrderItemDto orderItemDto);

    @Mapping(source = "orderItem.id", target = "orderItemId")
    @Mapping(source = "flavor.id", target = "flavorId")
    @Mapping(source = "flavor.name", target = "flavorName")
    @Mapping(source = "flavor.description", target = "flavorDescription")
    @Mapping(source = "flavor.ingredients", target = "flavorIngredients")
    @Mapping(source = "flavor.allergens", target = "flavorAllergens")
    @Mapping(source = "tier", target = "tier")
    OrderItemFlavorTierDto mapToOrderItemFlavorTierDto(OrderItemFlavorTier orderItemFlavorTier);

}
