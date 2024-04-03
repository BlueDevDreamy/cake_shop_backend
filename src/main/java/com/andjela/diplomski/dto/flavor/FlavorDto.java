package com.andjela.diplomski.dto.flavor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlavorDto {
    private String name;
    private String description;
    private String ingredients;
    private String allergens;
}
