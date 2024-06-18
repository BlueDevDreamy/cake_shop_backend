package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.address.AddressCreateDto;
import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.order.OrderCreateDto;
import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.OrderService;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreateDto,
                                                @RequestHeader("Authorization") String jwt) {
        // Implementacija logike za kreiranje narudžbe
        OrderDto orderDto = orderService.createOrder(orderCreateDto, jwt);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
//    @PostMapping
//    public ResponseEntity<OrderDto> createOrder(
//            @RequestBody AddressCreateDto addressDto,
//            @RequestBody List<CartItemDto> cartItems,
//            @RequestHeader("Authorization") String jwt
//    ) {
//        // Dobavljanje korisnika na osnovu JWT tokena
//        User user = userService.getUserByJwt(jwt);
//        UserDto userDto = UserMapper.MAPPER.mapToUserDTO(user);
//
//        // Poziv servisa za kreiranje narudžbe sa adresom i cartItems
//        OrderDto order = orderService.createOrder(userDto, addressDto, cartItems);
//
//        return new ResponseEntity<>(order, HttpStatus.CREATED);
//    }

    //Izmenila sam da ovde vraca obican Order umesto dto
    //Istestirano u POSTMAN-u
//    @PostMapping
//    public ResponseEntity<OrderDto> createOrder(@RequestBody AddressDto addressDto, @RequestHeader("Authorization") String jwt) {
//        User user = userService.getUserByJwt(jwt);
//        System.out.println("User u kontroleru: " + user);
//        UserDto userDto = UserMapper.MAPPER.mapToUserDTO(user);
////        System.out.println("UserDto u kontroleru: " + userDto);
//        OrderDto order = orderService.createOrder(userDto, addressDto);
//        return new ResponseEntity<>(order, HttpStatus.CREATED);
//    }
    //Istestirano u POSTMAN-u bez specificnog statusa porudzbine
    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> orderHistory(@RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        List<OrderDto> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }
    //Istestirano u POSTMAN-u
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt);
        System.out.println("U kontroleru" + orderId); // Dodajte ovu liniju za debug
        OrderDto orders = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

}
