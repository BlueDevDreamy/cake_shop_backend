package com.andjela.diplomski.dto.cake;

import com.andjela.diplomski.entity.codebook.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeDto {
    private Long id;
    private String title;
    private int pricePerKilo;
    private int decorationPrice;
    private int minWeight;
    private int maxWeight;
    private int minTier;
    private int maxTier;
    private String imageUrl;
    private Category category;
}
