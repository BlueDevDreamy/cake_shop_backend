package com.andjela.diplomski.dto.review;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    //    private String review;
//    private ProductDto product;
//    private UserDto user;
    private Long productId;
    private String review;
}
