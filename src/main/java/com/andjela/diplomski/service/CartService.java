package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cart.CartMapper;
import com.andjela.diplomski.dto.cartItem.CartItemCreateDto;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.cartItem.CartItemMapper;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.*;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.andjela.diplomski.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CakeRepository cakeRepository;
    private final CartItemRepository cartItemRepository;
    private final FlavorRepository flavorRepository;

    @Override
    public CartDto createCart(UserDto userDto) {
        User user = UserMapper.MAPPER.mapToUser(userDto);
        Cart cart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        cartRepository.save(cart);
        return CartMapper.MAPPER.mapToCartDto(cart);
    }

    @Override
    public String addCartItem(Long userId, CartItemCreateDto req) {
        Cart cart = cartRepository.findUserById(userId);

        Cake cake = cakeRepository.findById(req.getCakeId())
                .orElseThrow(() -> new RuntimeException("Cake with id " + req.getCakeId() + " not found"));

        List<CartItemFlavorTier> cartItemFlavorTiers = new ArrayList<>();
        for (int tier = 0; tier < req.getFlavors().size(); tier++) {
            Long flavorId = req.getFlavors().get(tier);
            Flavor flavor = flavorRepository.findById(flavorId)
                    .orElseThrow(() -> new RuntimeException("Flavor with id " + flavorId + " not found"));

            CartItemFlavorTier cartItemFlavorTier = CartItemFlavorTier.builder()
                    .flavor(flavor)
                    .tier((long)tier + 1)
                    .createdAt(LocalDateTime.now())
                    .build();
            cartItemFlavorTiers.add(cartItemFlavorTier);
        }

        CartItem cartItem = createCartItem(userId, req, cake, cart, cartItemFlavorTiers);
        cartItemRepository.save(cartItem);

        return "Item added to cart";
    }

    private CartItem createCartItem(Long userId, CartItemCreateDto req, Cake cake, Cart cart, List<CartItemFlavorTier> cartItemFlavorTiers) {
        CartItem cartItem = CartItem.builder()
                .selectedWeight(req.getSelectedWeight())
                .selectedTiers(req.getSelectedTiers())
                .piecesNumber(req.getPiecesNumber())
                .totalPrice(req.getTotalPrice())
                .cake(cake)
                .note(req.getNote())
                .fakeTier(req.getFakeTier())
                .cart(cart)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();

        cartItemFlavorTiers.forEach(cartItemFlavorTier -> cartItemFlavorTier.setCartItem(cartItem));
        cartItem.setCartItemFlavorTiers(cartItemFlavorTiers);

        return cartItem;
    }

    public CartDto getUserCart(Long userId) {
        Cart cart = cartRepository.findUserById(userId);
        int totalPrice = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getTotalPrice();
        }
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);

        CartDto cartDto = CartMapper.MAPPER.mapToCartDto(cart);
        List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(cartItem -> {
                    CartItemDto cartItemDto = CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
                    cartItemDto.setFlavors(CartItemMapper.MAPPER.mapToCartItemFlavorTierDtoList(cartItem.getCartItemFlavorTiers()));
                    return cartItemDto;
                })
                .collect(Collectors.toList());
        cartDto.setCartItems(cartItemDtos);

        return cartDto;
    }

}
