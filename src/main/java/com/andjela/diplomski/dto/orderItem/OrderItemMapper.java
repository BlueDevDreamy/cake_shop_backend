package com.andjela.diplomski.dto.orderItem;

import com.andjela.diplomski.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper MAPPER = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDto mapToOrderItemDto(OrderItem orderItem);
    OrderItem mapToOrderItem(OrderItemDto orderItemDto);
}
