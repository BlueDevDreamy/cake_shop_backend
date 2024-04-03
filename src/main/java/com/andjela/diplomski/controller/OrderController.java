package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.OrderService;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    //ToDo maybe createOrder should use User instead of UserDto
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody AddressDto addressDto, @RequestHeader("Authorization") String jwt){
        User user = userService.getUserByJwt(jwt);
        UserDto userDto = UserMapper.MAPPER.mapToUserDTO(user);
        OrderDto orderDto = orderService.createOrder(userDto, addressDto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
