package com.andjela.diplomski.repository;

import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci " +
            "WHERE ci.cart = :cart " +
            "AND ci.cake = :cake " +
            "AND ci.userId =:userId")
    CartItem isCartItemExists(@Param("cart") Cart cart,
                              @Param("cake") Cake cake,
                              @Param("userId") Long userId);

    List<CartItem> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
