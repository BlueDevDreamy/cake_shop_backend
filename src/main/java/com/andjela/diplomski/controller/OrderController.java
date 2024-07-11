package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.order.OrderCreateDto;
import com.andjela.diplomski.dto.order.OrderDto;
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
        OrderDto orderDto = orderService.createOrder(orderCreateDto, jwt);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> orderHistory(@RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        List<OrderDto> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    //Istestirano u POSTMAN-u
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        OrderDto orders = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

}
