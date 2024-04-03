package com.andjela.diplomski.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private Long productId;
    private String weight;
    private int quantity;
    private Integer price;
}
