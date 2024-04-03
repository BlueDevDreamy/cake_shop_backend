package com.andjela.diplomski.dto.rating;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    //private UserDto user;
    //private ProductDto product;
    private Long productId;
    private Double rating;
}
