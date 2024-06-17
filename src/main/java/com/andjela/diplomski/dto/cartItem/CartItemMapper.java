package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.dto.cartItemFlavorTier.CartItemFlavorTierDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.entity.CartItem;
import com.andjela.diplomski.entity.CartItemFlavorTier;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartItemMapper {

    CartItemMapper MAPPER = Mappers.getMapper(CartItemMapper.class);

    @Mapping(source = "cake.id", target = "cakeId")
    @Mapping(source = "cake.title", target = "cakeTitle")
    @Mapping(source = "cake.imageUrl", target = "cakeImageUrl")
    @Mapping(target = "flavors", source = "cartItemFlavorTiers")
    CartItemDto mapToCartItemDto(CartItem cartItem);

//    CartItem mapToCartItem(CartItemDto cartItemDto);

    @Mapping(source = "cartItem.id", target = "cartItemId")
    @Mapping(source = "flavor.id", target = "flavorId")
    @Mapping(source = "flavor.name", target = "flavorName")
    @Mapping(source = "flavor.description", target = "flavorDescription")
    @Mapping(source = "flavor.ingredients", target = "flavorIngredients")
    @Mapping(source = "flavor.allergens", target = "flavorAllergens")
    @Mapping(source = "tier", target = "tier")
    CartItemFlavorTierDto mapToCartItemFlavorTierDto(CartItemFlavorTier cartItemFlavorTier);

    List<CartItemFlavorTierDto> mapToCartItemFlavorTierDtoList(List<CartItemFlavorTier> cartItemFlavorTiers);

    @InheritInverseConfiguration
    CartItem mapToCartItem(CartItemDto cartItemDto);

}
