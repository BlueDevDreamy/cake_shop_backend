package com.andjela.diplomski.dto.order;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.entity.Order;
import com.andjela.diplomski.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderDto mapToOrderDto(Order order);
    Order mapToOrder(OrderDto orderDto);

    List<OrderDto> mapToListOrderDto(List<Order> orders);
}
