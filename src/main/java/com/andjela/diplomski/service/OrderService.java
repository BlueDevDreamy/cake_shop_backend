package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressMapper;
import com.andjela.diplomski.dto.order.OrderCreateDto;
import com.andjela.diplomski.dto.order.OrderDto;
import com.andjela.diplomski.dto.order.OrderMapper;
import com.andjela.diplomski.entity.*;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

//    @Override
//    @Transactional
//    public OrderDto createOrder(OrderCreateDto orderCreateDto, String jwt) {
//        User user = userService.getUserByJwt(jwt);
//
//        Address shippingAddress = AddressMapper.MAPPER.mapToAddress(orderCreateDto.getAddressDto());
//        shippingAddress.setCreatedAt(LocalDateTime.now());
//        shippingAddress = addressRepository.save(shippingAddress);
//
//        if (user.getAddresses() == null) {
//            user.setAddresses(new ArrayList<>());
//        }
//        user.getAddresses().add(shippingAddress);
//        user = userRepository.save(user);
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setShippingAddress(shippingAddress);
//        order.setOrderDate(orderCreateDto.getOrderDate());
//        order.setDeliveryDate(orderCreateDto.getDeliveryDate());
//        order.setTotalPrice(orderCreateDto.getTotalPrice());
//        order.setOrderStatus(orderCreateDto.getOrderStatus());
//        order.setTotalItem(orderCreateDto.getTotalItem());
//        order.setOrderNumber(generateOrderNumber());
//        order.setCreatedAt(LocalDateTime.now());
//
//        List<Long> cartItemIds = orderCreateDto.getCartItems();
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (Long cartItemId : cartItemIds) {
//            CartItem cartItem = cartItemRepository.findById(cartItemId)
//                    .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + cartItemId));
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setSelectedWeight(cartItem.getSelectedWeight());
//            orderItem.setSelectedTiers(cartItem.getSelectedTiers());
//            orderItem.setPiecesNumber(cartItem.getPiecesNumber());
//            orderItem.setTotalPrice(cartItem.getTotalPrice());
//            orderItem.setCart(cartItem.getCart());
//            orderItem.setUserId(user.getId());
//            orderItem.setCake(cartItem.getCake());
//
//            List<OrderItemFlavorTier> orderItemFlavorTiers = new ArrayList<>();
//            for (CartItemFlavorTier cartItemFlavorTier : cartItem.getCartItemFlavorTiers()) {
//                OrderItemFlavorTier orderItemFlavorTier = new OrderItemFlavorTier();
//                orderItemFlavorTier.setOrderItem(orderItem);
//                orderItemFlavorTier.setFlavor(cartItemFlavorTier.getFlavor());
//                orderItemFlavorTier.setTier(cartItemFlavorTier.getTier());
//                orderItemFlavorTier.setCreatedAt(LocalDateTime.now());
//                orderItemFlavorTiers.add(orderItemFlavorTier);
//            }
//            orderItem.setCreatedAt(LocalDateTime.now());
//            orderItem.setOrderItemFlavorTiers(orderItemFlavorTiers);
//
//            orderItems.add(orderItem);
//        }
//
//        order.setOrderItems(orderItems);
//
//        order = orderRepository.save(order);
//
//        return OrderMapper.MAPPER.mapToOrderDto(order);
//    }
@Override
@Transactional
public OrderDto createOrder(OrderCreateDto orderCreateDto, String jwt) {
    User user = userService.getUserByJwt(jwt);
    Address shippingAddress;

    if (orderCreateDto.getAddressDto().getId() != null) {
        System.out.println("Ovde ide u if " + orderCreateDto.getAddressDto().getId());
        shippingAddress = addressRepository.findById(orderCreateDto.getAddressDto().getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + orderCreateDto.getAddressDto().getId()));
    } else {
        shippingAddress = AddressMapper.MAPPER.mapToAddress(orderCreateDto.getAddressDto());
        shippingAddress.setCreatedAt(LocalDateTime.now());
        shippingAddress = addressRepository.save(shippingAddress);

        if (user.getAddresses() == null) {
            user.setAddresses(new ArrayList<>());
        }
        user.getAddresses().add(shippingAddress);
        user = userRepository.save(user);
    }
    Order order = new Order();
    order.setUser(user);
    order.setShippingAddress(shippingAddress);
    order.setOrderDate(orderCreateDto.getOrderDate());
    order.setDeliveryDate(orderCreateDto.getDeliveryDate());
    order.setTotalPrice(orderCreateDto.getTotalPrice());
    order.setOrderStatus(orderCreateDto.getOrderStatus());
    order.setTotalItem(orderCreateDto.getTotalItem());
    order.setOrderNumber(generateOrderNumber());
    order.setCreatedAt(LocalDateTime.now());

    List<Long> cartItemIds = orderCreateDto.getCartItems();
    List<OrderItem> orderItems = new ArrayList<>();

    for (Long cartItemId : cartItemIds) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + cartItemId));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setSelectedWeight(cartItem.getSelectedWeight());
        orderItem.setSelectedTiers(cartItem.getSelectedTiers());
        orderItem.setPiecesNumber(cartItem.getPiecesNumber());
        orderItem.setTotalPrice(cartItem.getTotalPrice());
        orderItem.setCart(cartItem.getCart());
        orderItem.setUserId(user.getId());
        orderItem.setCake(cartItem.getCake());

        List<OrderItemFlavorTier> orderItemFlavorTiers = new ArrayList<>();
        for (CartItemFlavorTier cartItemFlavorTier : cartItem.getCartItemFlavorTiers()) {
            OrderItemFlavorTier orderItemFlavorTier = new OrderItemFlavorTier();
            orderItemFlavorTier.setOrderItem(orderItem);
            orderItemFlavorTier.setFlavor(cartItemFlavorTier.getFlavor());
            orderItemFlavorTier.setTier(cartItemFlavorTier.getTier());
            orderItemFlavorTier.setCreatedAt(LocalDateTime.now());
            orderItemFlavorTiers.add(orderItemFlavorTier);
        }
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setOrderItemFlavorTiers(orderItemFlavorTiers);

        orderItems.add(orderItem);
    }

    order.setOrderItems(orderItems);

    order = orderRepository.save(order);

    return OrderMapper.MAPPER.mapToOrderDto(order);
}



    @Transactional
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

    @Transactional
    public void updateOrderAfterPayment(String paymentId, boolean isPaid, LocalDateTime paymentDate, String paymentMethod, String transactionId) {
        Order order = orderRepository.findByPaymentId(paymentId);
        if (order != null) {
            order.setPaid(isPaid);
            order.setPaymentDate(paymentDate);
            order.setPaymentMethod(paymentMethod);
            order.setTransactionId(transactionId);
            orderRepository.save(order);
        } else {
            throw new EntityNotFoundException("Order not found with paymentId: " + paymentId);
        }
    }

    private String generateOrderNumber() {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString().replace("-", "");

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().substring(0, 4);

        String orderNumber = formattedDate + "-" + uuidString;
        return orderNumber;
    }
}
