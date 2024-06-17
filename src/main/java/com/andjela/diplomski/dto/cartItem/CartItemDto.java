package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cartItemFlavorTier.CartItemFlavorTierDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.codebook.Flavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private double selectedWeight;
    private int selectedTiers;
    private int piecesNumber;
    private int totalPrice;
    private Long cakeId;
    private String cakeTitle;
    private String cakeImageUrl;
    private List<CartItemFlavorTierDto> flavors; // Promenjeno u CartItemFlavorTierDto
    private String note;
    private int fakeTier;
    private Long userId;
}




