package com.andjela.diplomski.dto.order;

import com.andjela.diplomski.dto.orderItem.OrderItemDto;
import com.andjela.diplomski.dto.orderItem.OrderItemMapper;
import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    //    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "getOrderItemDto")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto mapToOrderDto(Order order);

    Order mapToOrder(OrderDto orderDto);

    List<OrderDto> mapToListOrderDto(List<Order> orders);

//    @Named("getOrderItemDto")
//    default OrderItemDto getOrderItemDto(OrderItem orderItem) {
//        return OrderItemMapper.MAPPER.mapToOrderItemDto(orderItem);
//    }
}
