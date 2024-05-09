package com.andjela.diplomski.request;

import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.codebook.Flavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

//    private Long cakeId;
//    private int price;
    private double selectedWeight;
    private int selectedTiers;
    private int piecesNumber;
    private int totalPrice;
    private Long cakeId;
    private List<Flavor> flavors;
    private String note;
    private int fakeTier;
}
