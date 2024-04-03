package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.CartService;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CartDto> getUserCart(@RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt); //fali mi metoda getUserByJwt u User servisu
        CartDto cartDto = cartService.getUserCart(user.getId());
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
