package com.andjela.diplomski.dto.cartItem;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdateDto {
    @Min(value = 2, message = "Selected weight must be greater than 2")
    @Max(value = 30, message = "Selected weight must be less than 30")
    private double selectedWeight;
    @Min(value = 1, message = "Min tier must be greater than 1")
    @Max(value = 6, message = "Max tier must be less than 6")
    private int selectedTiers;
    private int piecesNumber;
    @Min(value = 0, message = "Price per kilo must be a positive number")
    private int totalPrice;
    @NotNull(message = "Cake ID is mandatory")
    private Long cakeId;
    private List<String> flavors;
    private String note;
    private int fakeTier;
}
