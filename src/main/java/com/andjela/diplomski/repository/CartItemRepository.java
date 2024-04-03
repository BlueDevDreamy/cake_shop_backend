package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.CartItem;
import com.andjela.diplomski.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci " +
            "WHERE ci.cart = :cart " +
            "AND ci.product = :product " +
            "AND ci.weight = :weight " +
            "AND ci.userId =:userId")
    CartItem isCartItemExists(@Param("cart") Cart cart,
                              @Param("product")Product product,
                              @Param("weight") String weight,
                              @Param("userId") Long userId);
}
