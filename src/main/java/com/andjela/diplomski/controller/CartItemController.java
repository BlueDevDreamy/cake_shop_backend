package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.CartItemService;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;

    //ToDo createCartItem
    @PostMapping
    public ResponseEntity<CartItemDto> createCartItem(CartItemDto c, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt);
        CartItemDto cartItemDto = cartItemService.createCartItem(c);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@RequestBody CartItemDto cartItemDto, @PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt);
        CartItemDto updatedCartItemDto = cartItemService.updateCartItem(user.getId(), id, cartItemDto);
        return new ResponseEntity<>(updatedCartItemDto, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
}
