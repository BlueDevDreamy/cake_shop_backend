package com.andjela.diplomski.dto.cartItem;

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

    private double selectedWeight;
    private int selectedTiers;
    private int piecesNumber;
    private int totalPrice;
    private Cake cake;
    private List<Flavor> flavors;
    private String note;
    private int fakeTier;
    private Cart cart;
    private Long userId;
}
