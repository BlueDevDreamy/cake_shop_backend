package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private CartDto cart;
    private ProductDto product;
    private int quantity;
    private Double weight;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
}
