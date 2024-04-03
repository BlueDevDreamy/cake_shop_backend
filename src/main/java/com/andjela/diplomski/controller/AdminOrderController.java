package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
    @PutMapping("{id}/confirmed")
    public ResponseEntity<OrderDto> confirmedOrder(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        OrderDto orderDto = orderService.confirmedOrder(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @PutMapping("{id}/shipped")
    public ResponseEntity<OrderDto> shippedOrder(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        OrderDto orderDto = orderService.shippedOrder(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @PutMapping("{id}/delivered")
    public ResponseEntity<OrderDto> deliveredOrder(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        OrderDto orderDto = orderService.deliveredOrder(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @PutMapping("{id}/canceled")
    public ResponseEntity<OrderDto> canceledOrder(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        OrderDto orderDto = orderService.canceledOrder(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id, @RequestHeader("Authorization") String jwt){
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
}
