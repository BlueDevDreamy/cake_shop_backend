package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressDto;

import com.andjela.diplomski.dto.address.AddressMapper;
import com.andjela.diplomski.dto.cake.CakeMapper;
import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cart.CartMapper;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.order.OrderDto;

import com.andjela.diplomski.dto.order.OrderMapper;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.*;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto createOrder(UserDto userDto, AddressDto shippingAddress) {
//        shippingAddress.setUser(userDto);
        Address address = AddressMapper.MAPPER.mapToAddress(shippingAddress);
        addressRepository.save(address);
        userDto.getAddress().add(address);
        User user = UserMapper.MAPPER.mapToUser(userDto);
        userRepository.save(user);

        CartDto cartDto = cartService.getUserCart(user.getId());
        Cart cart = CartMapper.MAPPER.mapToCart(cartDto);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemDto item : cartDto.getCartItems()) {
            OrderItem orderItem = OrderItem.builder()
//                    .cake(item.getCake())
//                    .userId(item.getUserId())
                    .createdAt(LocalDateTime.now())
                    .build();
            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }
        Order createdOrder = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .totalPrice(cart.getTotalPrice())
                .totalItem(cart.getTotalItem())
                .shippingAddress(address)
                .orderDate(LocalDateTime.now())
                .orderStatus("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
        Order savedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }
        return OrderMapper.MAPPER.mapToOrderDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find order with id:" + id));
        return OrderMapper.MAPPER.mapToOrderDto(order);
    }

    @Override
    public List<OrderDto> usersOrderHistory(Long userId) {
        List<Order> usersOrders = orderRepository.getUsersOrders(userId);
        return OrderMapper.MAPPER.mapToListOrderDto(usersOrders);
    }

    @Override
    public OrderDto placeOrder(Long id) {
        OrderDto orderDto = getOrderById(id);
        orderDto.setOrderStatus("PLACED");
        orderDto.getPaymentDetails().setPaymentStatus("COMPLETED");
        orderRepository.save(OrderMapper.MAPPER.mapToOrder(orderDto));
        return orderDto;
    }

    @Override
    public OrderDto confirmedOrder(Long id) {
        OrderDto orderDto = getOrderById(id);
        orderDto.setOrderStatus("CONFIRMED");
        orderRepository.save(OrderMapper.MAPPER.mapToOrder(orderDto));
        return orderDto;
    }

    @Override
    public OrderDto shippedOrder(Long id) {
        OrderDto orderDto = getOrderById(id);
        orderDto.setOrderStatus("SHIPPED");
        orderRepository.save(OrderMapper.MAPPER.mapToOrder(orderDto));
        return orderDto;
    }

    @Override
    public OrderDto deliveredOrder(Long id) {
        OrderDto orderDto = getOrderById(id);
        orderDto.setOrderStatus("DELIVERED");
        orderRepository.save(OrderMapper.MAPPER.mapToOrder(orderDto));
        return orderDto;
    }

    @Override
    public OrderDto canceledOrder(Long id) {
        OrderDto orderDto = getOrderById(id);
        orderDto.setOrderStatus("CANCELED");
        orderRepository.save(OrderMapper.MAPPER.mapToOrder(orderDto));
        return orderDto;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderDto> foundOrders = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("List of orders is empty");
        }
        for (Order o : orders) {
            OrderDto orderDto = OrderMapper.MAPPER.mapToOrderDto(o);
            foundOrders.add(orderDto);
        }
        return foundOrders;
    }

    @Override
    public String deleteOrder(Long id) {
        Order foundOrder = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find order with id:" + id));
        orderRepository.delete(foundOrder);
        return "Order deleted successfully";
    }
}
