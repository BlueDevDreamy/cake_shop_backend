package com.andjela.diplomski.dto.cake;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CakeUpdateDto {
    @Size(min = 2, max = 50, message = "Title must have 2 or more characters")
    private String title;
    @Min(value = 0, message = "Price per kilo must be a positive number")
    private int pricePerKilo;
    private int decorationPrice;
    @Min(value = 2, message = "Min weight must be greater than 2")
    private int minWeight;
    @Max(value = 30, message = "Max weight must be less than 30")
    private int maxWeight;
    @Min(value = 1, message = "Min tier must be greater than 1")
    private int minTier;
    @Max(value = 6, message = "Max tier must be less than 6")
    private int maxTier;
    @NotBlank(message = "Image url must not be empty")
    private String imageUrl;
    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;
}
