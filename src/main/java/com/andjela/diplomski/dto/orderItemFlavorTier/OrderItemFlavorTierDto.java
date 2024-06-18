package com.andjela.diplomski.dto.orderItemFlavorTier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemFlavorTierDto {
    private Long id;
    private Long orderItemId; // dodajte id cartItem-a za referencu
    private Long flavorId;
    private String flavorName;
    private String flavorDescription;
    private String flavorIngredients;
    private String flavorAllergens;
    private Long tier;
}
