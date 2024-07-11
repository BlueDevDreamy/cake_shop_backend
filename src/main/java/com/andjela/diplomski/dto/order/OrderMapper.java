package com.andjela.diplomski.dto.order;

import com.andjela.diplomski.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto mapToOrderDto(Order order);

    List<OrderDto> mapToListOrderDto(List<Order> orders);

}
