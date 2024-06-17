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
import jakarta.transaction.Transactional;
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
//        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
//        System.out.println(cartItemDto.getNote());
////        Cake cake = cakeRepository.findById(cartItem.getCake().getId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id " + cartItemDto.getCakeId()));
//        Cake cake = cakeRepository.findById(cartItemDto.getCakeId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cake with id " + cartItemDto.getCakeId()));
//        Cart cart = cartRepository.findById(cartItemDto.getCart().getId()).orElseThrow(() -> new ResourceNotFoundException("Didn't find cart with id " + cartItem.getCart().getId()));
//
//        CartItem createCartItem = CartItem.builder()
//                .selectedWeight(cartItemDto.getSelectedWeight())
//                .selectedTiers(cartItemDto.getSelectedTiers())
//                //Mozda ovde izracunati pieces number i price
//                .piecesNumber(cartItemDto.getPiecesNumber())
//                .totalPrice(cartItemDto.getTotalPrice())
//                .cake(cake)
////                .flavors(cartItemDto.getFlavors())
//                .note(cartItemDto.getNote())
//                .fakeTier(cartItemDto.getFakeTier())
////                .cart(cartItemDto.getCart())
//                .cart(cart)
//                .userId(cartItem.getUserId())
//                .build();
//        cartItemRepository.save(createCartItem);
//        return CartItemMapper.MAPPER.mapToCartItemDto(createCartItem);
        return null;
    }

    @Override
    public CartItemDto updateCartItem(Long userId, Long id, CartItemDto cartItemDto) {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with id " + id + " not found"));

        if (!existingCartItem.getUserId().equals(userId)) {
//            throw new AuthorizationException("You are not authorized to update this cart item");
            System.out.println("You are not authorized to update this cart item");
        }
        existingCartItem.setSelectedWeight(cartItemDto.getSelectedWeight());
        existingCartItem.setPiecesNumber(cartItemDto.getPiecesNumber());
        existingCartItem.setTotalPrice(cartItemDto.getTotalPrice());
        existingCartItem.setNote(cartItemDto.getNote());

        // Čuvamo promene u bazi podataka
        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);

        // Mapiramo ažurirani cart item u DTO format pre nego što ga vratimo
        return CartItemMapper.MAPPER.mapToCartItemDto(updatedCartItem);
    }

    @Transactional
    public CartItemDto increaseCartItemWeight(Long userId, Long cartItemId) {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with id " + cartItemId + " not found"));

        if (!existingCartItem.getUserId().equals(userId)) {
//            throw new AuthorizationException("You are not authorized to update this cart item");
            System.out.println("You are not authorized to update this cart item");

        }

        if(existingCartItem.getSelectedWeight() <= existingCartItem.getCake().getMaxWeight()){
            existingCartItem.setSelectedWeight(existingCartItem.getSelectedWeight() + 1);
            existingCartItem.setPiecesNumber(existingCartItem.getPiecesNumber() + 8); //U jednom kg ima 8 parcica
            existingCartItem.setTotalPrice(existingCartItem.getTotalPrice() + existingCartItem.getCake().getPricePerKilo());
        }else {
            System.out.println("Exceeded maximum weight of this cart item");
        }
        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);

        return CartItemMapper.MAPPER.mapToCartItemDto(updatedCartItem);
    }

    @Transactional
    public CartItemDto decreaseCartItemWeight(Long userId, Long cartItemId) {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with id " + cartItemId + " not found"));

        if (!existingCartItem.getUserId().equals(userId)) {
//            throw new AuthorizationException("You are not authorized to update this cart item");
            System.out.println("You are not authorized to update this cart item");

        }

        if(existingCartItem.getSelectedWeight() <= existingCartItem.getCake().getMaxWeight()){
            existingCartItem.setSelectedWeight(existingCartItem.getSelectedWeight() - 1);
            existingCartItem.setPiecesNumber(existingCartItem.getPiecesNumber() - 8); //U jednom kg ima 8 parcica
            existingCartItem.setTotalPrice(existingCartItem.getTotalPrice() - existingCartItem.getCake().getPricePerKilo());
        }else {
            System.out.println("Selected weight is under minimum weight of this cart item");
        }
        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);

        return CartItemMapper.MAPPER.mapToCartItemDto(updatedCartItem);
    }

    @Override
    public CartItemDto isCartItemExists(CartDto cartDto, CakeDto cakeDto, Long userId) {
        Cart cart = CartMapper.MAPPER.mapToCart(cartDto);
        Cake cake = CakeMapper.MAPPER.mapToCake(cakeDto);
        CartItem cartItem = cartItemRepository.isCartItemExists(cart, cake, userId);
        if(cartItem == null) {
            throw new ResourceNotFoundException("No such cart item exists");
        }
        return CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
    }

    @Override
    public String removeCartItem(Long userId, Long cartItemId) {
        CartItemDto cartItemDto = findCartItemById(cartItemId);
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);

        UserDto userDto = userService.getUserById(cartItem.getUserId());
        UserDto foundUserDto = userService.getUserById(userId);

        if (userDto.getId().equals(foundUserDto.getId())) {
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
