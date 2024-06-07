package com.andjela.diplomski.dto.cartItem;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.codebook.Flavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private double selectedWeight;
    private int selectedTiers;
    private int piecesNumber; //moze na frontu da se izracuna
    private int totalPrice;
    private Long cakeId;
    private List<Flavor> flavors;
    private String note;
    private int fakeTier;
//    private CartDto cart;
    private Long userId;

    //piece number ne treba da se nalazi u dto, jer ja treba us servicu da izracunam na osnovu unete tezine
    //total ne treba da se nalazi u dto, jer ja treba us servicu da izracunam na osnovu unete tezine i cene odabrane torte
    //mozda je lakse da imam prosledjen cake id a ne ceo objekat

    //Ne znam sta da radim sa cart i userid

    @Override
    public String toString() {
        return "CartItemDto{" +
                "selectedWeight=" + selectedWeight +
                ", selectedTiers=" + selectedTiers +
                ", piecesNumber=" + piecesNumber +
                ", totalPrice=" + totalPrice +
                ", cakeId=" + cakeId +
                ", flavors=" + flavors +
                ", note='" + note + '\'' +
                ", fakeTier=" + fakeTier +
//                ", cart=" + cart +
                ", userId=" + userId +
                '}';
    }
}
