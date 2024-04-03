package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressDto;
import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.entity.Address;

import java.util.List;

public interface IOrderService {
    OrderDto createOrder(UserDto user, AddressDto shippingAddress);
    OrderDto getOrderById(Long id);
    List<OrderDto> usersOrderHistory(Long userId);
    OrderDto placeOrder(Long id);
    OrderDto confirmedOrder(Long id);
    OrderDto shippedOrder(Long id);
    OrderDto deliveredOrder(Long id);
    OrderDto canceledOrder(Long id);
    List<OrderDto> getAllOrders();
    String deleteOrder(Long id);
}
