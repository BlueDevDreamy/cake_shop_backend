package com.andjela.diplomski.dto.flavor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlavorCreateDto {
    @NotBlank(message = "Name must not be empty")
    private String name;
    private String description;
    private String ingredients;
    private String allergens;
}
