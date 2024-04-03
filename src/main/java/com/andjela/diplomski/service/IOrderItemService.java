package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.orderItem.OrderItemDto;

public interface IOrderItemService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);
}
