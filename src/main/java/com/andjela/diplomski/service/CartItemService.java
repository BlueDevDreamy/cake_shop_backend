package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cake.CakeDto;
import com.andjela.diplomski.dto.cake.CakeMapper;
import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cart.CartMapper;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.cartItem.CartItemMapper;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.entity.Cake;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.CartItem;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.CakeRepository;
import com.andjela.diplomski.repository.CartItemRepository;
import com.andjela.diplomski.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final CakeRepository cakeRepository;

    @Override
    public CartItemDto createCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        System.out.println(cartItemDto.getNote());
//        Cake cake = cakeRepository.findById(cartItem.getCake().getId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id " + cartItemDto.getCakeId()));
        Cake cake = cakeRepository.findById(cartItemDto.getCakeId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id " + cartItemDto.getCakeId()));
//        Cart cart = cartRepository.findById(cartItemDto.getCart().getId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cart with id " + cartItem.getCart().getId()));

        CartItem createCartItem = CartItem.builder()
                .selectedWeight(cartItemDto.getSelectedWeight())
                .selectedTiers(cartItemDto.getSelectedTiers())
                //Mozda ovde izracunati pieces number i price
                .piecesNumber(cartItemDto.getPiecesNumber())
                .totalPrice(cartItemDto.getTotalPrice())
                .cake(cake)
                .flavors(cartItemDto.getFlavors())
                .note(cartItemDto.getNote())
                .fakeTier(cartItemDto.getFakeTier())
                .cart(cartItemDto.getCart())
                .userId(cartItem.getUserId())
                .build();
        cartItemRepository.save(createCartItem);
        return CartItemMapper.MAPPER.mapToCartItemDto(createCartItem);
    }

    @Override
    public CartItemDto updateCartItem(Long userId, Long id, CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        CartItem updatedCartItem = cartItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find cart item with id: " + id));
        Cake cake = cakeRepository.findById(cartItemDto.getCakeId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id " + cartItemDto.getCakeId()));
        UserDto userDto = userService.getUserById(userId);

        if (userDto.getId().equals(userId)) {
            updatedCartItem.setSelectedWeight(cartItem.getSelectedWeight());
            updatedCartItem.setSelectedTiers(cartItem.getSelectedTiers());
            updatedCartItem.setPiecesNumber(cartItem.getPiecesNumber());
            updatedCartItem.setTotalPrice(cartItem.getTotalPrice());
            updatedCartItem.setCake(cake);
            updatedCartItem.setFlavors(cartItem.getFlavors());
            updatedCartItem.setNote(cartItem.getNote());
            updatedCartItem.setFakeTier(cartItem.getFakeTier());
            updatedCartItem.setCart(cartItemDto.getCart());
        }
        cartItemRepository.save(updatedCartItem);
        return CartItemMapper.MAPPER.mapToCartItemDto(updatedCartItem);
    }

    @Override
    public CartItemDto isCartItemExists(CartDto cartDto, CakeDto cakeDto, Long userId) {
        Cart cart = CartMapper.MAPPER.mapToCart(cartDto);
        System.out.println("Cart " + cart);
        System.out.println("Cart DTO " + cartDto);


        Cake cake = CakeMapper.MAPPER.mapToCake(cakeDto);
        System.out.println("Cake " + cake);

        CartItem cartItem = cartItemRepository.isCartItemExists(cart, cake, userId);
        System.out.println("CartItem  " + cartItem);

        return CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
    }

    //ToDo check wht there is unreachable statement
    @Override
    public String removeCartItem(Long userId, Long cartItemId) {
        CartItemDto cartItemDto = findCartItemById(cartItemId);
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        UserDto userDto = userService.getUserById(cartItem.getUserId());
        UserDto foundUserDto = userService.getUserById(userId);
        if (userDto.getId().equals(foundUserDto)) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new ResourceNotFoundException("You can't remove another users item");
        }
        return "Successfully deleted cart item";
    }

    @Override
    public CartItemDto findCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Didn't find cart item with id: " + id));
        return CartItemMapper.MAPPER.mapToCartItemDto(cartItem);

    }
}
