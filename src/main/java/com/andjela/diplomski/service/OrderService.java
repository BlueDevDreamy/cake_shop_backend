package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.address.AddressCreateDto;
import com.andjela.diplomski.dto.address.AddressDto;

import com.andjela.diplomski.dto.address.AddressMapper;
import com.andjela.diplomski.dto.cake.CakeMapper;
import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cart.CartMapper;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.cartItem.CartItemMapper;
import com.andjela.diplomski.dto.cartItemFlavorTier.CartItemFlavorTierDto;
import com.andjela.diplomski.dto.order.OrderCreateDto;
import com.andjela.diplomski.dto.order.OrderDto;

import com.andjela.diplomski.dto.order.OrderMapper;
import com.andjela.diplomski.dto.orderItem.OrderItemDto;
import com.andjela.diplomski.dto.orderItem.OrderItemMapper;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.*;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressService addressService;
    private final CartItemRepository cartItemRepository;
    private final FlavorRepository flavorRepository;
    private final UserService userService;

    @Override
    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDto, String jwt) {
        // Dobavljanje korisnika na osnovu JWT tokena
        User user = userService.getUserByJwt(jwt);
//        User user = userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(jwt))
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Mapiranje AddressCreateDto u Address entitet i sačuvanje adrese
        Address shippingAddress = AddressMapper.MAPPER.mapToAddress(orderCreateDto.getAddressDto());
        shippingAddress.setCreatedAt(LocalDateTime.now());
        shippingAddress = addressRepository.save(shippingAddress);  // Sačuvaj adresu

        // Dodavanje adrese korisniku
        if (user.getAddresses() == null) {
            user.setAddresses(new ArrayList<>());
        }
        user.getAddresses().add(shippingAddress);
        user = userRepository.save(user); // Ažuriraj korisnika sa novom adresom

        // Mapiranje OrderCreateDto u Order entitet
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(orderCreateDto.getOrderDate());
        order.setDeliveryDate(orderCreateDto.getDeliveryDate());
        order.setTotalPrice(orderCreateDto.getTotalPrice());
        order.setOrderStatus(orderCreateDto.getOrderStatus());
        order.setTotalItem(orderCreateDto.getTotalItem());
        order.setCreatedAt(LocalDateTime.now());

        // Dohvatanje CartItem-ova iz baze na osnovu ID-jeva navedenih u orderCreateDto
        List<Long> cartItemIds = orderCreateDto.getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + cartItemId));

            // Mapiranje CartItem u OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Postavljanje veze ka narudžbi
            orderItem.setSelectedWeight(cartItem.getSelectedWeight());
            orderItem.setSelectedTiers(cartItem.getSelectedTiers()); // Podešavanje odabranih slojeva
            orderItem.setPiecesNumber(cartItem.getPiecesNumber());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setCart(cartItem.getCart());
            orderItem.setUserId(user.getId());
            orderItem.setCake(cartItem.getCake()); // Postavljanje torte

            // Mapiranje CartItemFlavorTier u OrderItemFlavorTier
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

            // Dodavanje orderItem u listu orderItems
            orderItems.add(orderItem);
        }

        // Postavljanje OrderItem-ova u Order
        order.setOrderItems(orderItems);

        // Sačuvaj narudžbu u bazi
        order = orderRepository.save(order);

        // Mapiranje Order entitet u OrderDto
        return OrderMapper.MAPPER.mapToOrderDto(order);
    }

//    @Override
//    @Transactional
//    public OrderDto createOrder(OrderCreateDto orderCreateDto, String jwt) {
//        // Dobavljanje korisnika na osnovu JWT tokena
//        User user = userRepository.findById(orderCreateDto.getUserId())
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        // Mapiranje AddressCreateDto u Address entitet i sačuvanje adrese
//        Address shippingAddress = AddressMapper.MAPPER.mapToAddress(orderCreateDto.getAddressDto());
//        shippingAddress.setCreatedAt(LocalDateTime.now());
//        shippingAddress = addressRepository.save(shippingAddress);  // Sačuvaj adresu
//
//        // Mapiranje OrderCreateDto u Order entitet
//        Order order = new Order();
//        order.setUser(user);
//        order.setShippingAddress(shippingAddress);
//        order.setOrderDate(orderCreateDto.getOrderDate());
//        order.setDeliveryDate(orderCreateDto.getDeliveryDate());
//        order.setTotalPrice(orderCreateDto.getTotalPrice());
//        order.setOrderStatus(orderCreateDto.getOrderStatus());
//        order.setTotalItem(orderCreateDto.getTotalItem());
//        order.setCreatedAt(LocalDateTime.now());
//
//        // Dohvatanje CartItem-ova iz baze na osnovu ID-jeva navedenih u orderCreateDto
//        List<Long> cartItemIds = orderCreateDto.getCartItems();
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (Long cartItemId : cartItemIds) {
//            CartItem cartItem = cartItemRepository.findById(cartItemId)
//                    .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + cartItemId));
//
//            // Mapiranje CartItem u OrderItem
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order); // Postavljanje veze ka narudžbi
//            orderItem.setSelectedWeight(cartItem.getSelectedWeight());
//            orderItem.setSelectedLayers(cartItem.getSelectedTiers()); // Podešavanje odabranih slojeva
//            orderItem.setPiecesNumber(cartItem.getPiecesNumber());
//            orderItem.setTotalPrice(cartItem.getTotalPrice());
//            orderItem.setCake(cartItem.getCake()); // Postavljanje torte
//
//            // Mapiranje CartItemFlavorTier u OrderItemFlavorTier
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
//            // Dodavanje orderItem u listu orderItems
//            orderItems.add(orderItem);
//        }
//
//        // Postavljanje OrderItem-ova u Order
//        order.setOrderItems(orderItems);
//
//        // Sačuvaj narudžbu u bazi
//        order = orderRepository.save(order);
//
//        // Mapiranje Order entitet u OrderDto
//        return OrderMapper.MAPPER.mapToOrderDto(order);
//    }

    @Transactional
    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find order with id:" + id));
        for(OrderItem orderItem : order.getOrderItems()) {
            System.out.println(orderItem.getCake().getTitle());
            OrderItemDto orderItemDto = OrderItemMapper.MAPPER.mapToOrderItemDto(orderItem);
//            System.out.println(orderItemDto.getCakeTitle());

        }
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
//        orderDto.getPaymentDetails().setPaymentStatus("COMPLETED");
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

    private String generateOrderNumber() {
        // Dobavljanje trenutnog datuma
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString().replace("-", ""); // Formatiranje datuma bez crtica

        // Generisanje UUID
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().substring(0, 4); // Koristimo prvih četiri karaktera UUID-a

        // Spajanje datuma, znaka "-" i UUID-a
        String orderNumber = formattedDate + "-" + uuidString;
        return orderNumber;
    }
}
