package com.andjela.diplomski.dto.cake;

import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.dto.type.TypeDto;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.andjela.diplomski.entity.codebook.Type;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeDto {

    private String title;
    private Integer pricePerKilo;
    private Integer decorationPrice;
    private FlavorDto flavor;
    private TypeDto type;
    private Integer layer;
    private Double weight;
    private Integer pieceNumber;
    private Integer totalPrice;
    private String imageUrl;
}
