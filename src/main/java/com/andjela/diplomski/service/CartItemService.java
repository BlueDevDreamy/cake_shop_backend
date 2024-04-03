package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.cart.CartDto;
import com.andjela.diplomski.dto.cart.CartMapper;
import com.andjela.diplomski.dto.cartItem.CartItemDto;
import com.andjela.diplomski.dto.cartItem.CartItemMapper;
import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.product.ProductMapper;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.Cart;
import com.andjela.diplomski.entity.CartItem;
import com.andjela.diplomski.entity.Product;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.exception.ResourceNotFoundException;
import com.andjela.diplomski.repository.CartItemRepository;
import com.andjela.diplomski.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;
    @Override
    public CartItemDto createCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        CartItem createCartItem = CartItem.builder()
                .cart(cartItem.getCart())
                .product(cartItem.getProduct())
                .quantity(1)
                .price(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .discountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
        cartItemRepository.save(createCartItem);
        return CartItemMapper.MAPPER.mapToCartItemDto(createCartItem);
    }

    @Override
    public CartItemDto updateCartItem(Long userId, Long id, CartItemDto cartItemDto) {
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        CartItem updatedCartItem = cartItemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Didn't find cart item with id: " + id));
        UserDto userDto = userService.getUserById(userId);

        if(userDto.getId().equals(userId)){
            updatedCartItem.setQuantity(cartItem.getQuantity());
            updatedCartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            updatedCartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        }
        cartItemRepository.save(updatedCartItem);
        return CartItemMapper.MAPPER.mapToCartItemDto(updatedCartItem);
    }

    @Override
    public CartItemDto isCartItemExists(CartDto cartDto, ProductDto productDto, String weight, Long userId) {
        Cart cart = CartMapper.MAPPER.mapToCart(cartDto);
        Product product = ProductMapper.MAPPER.mapToProduct(productDto);
        CartItem cartItem = cartItemRepository.isCartItemExists(cart, product, weight, userId);
        return CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
    }

    @Override
    public String removeCartItem(Long userId, Long cartItemId) {
        CartItemDto cartItemDto = findCartItemById(cartItemId);
        CartItem cartItem = CartItemMapper.MAPPER.mapToCartItem(cartItemDto);
        UserDto userDto = userService.getUserById(cartItem.getUserId());
        UserDto foundUserDto = userService.getUserById(userId);
        if(userDto.getId().equals(foundUserDto)){
            cartItemRepository.deleteById(cartItemId);
        }else {
            throw new ResourceNotFoundException("You can't remove another users item");
        }
        return "Successfully deleted cart item";
    }

    @Override
    public CartItemDto findCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Didn't find cart item with id: " + id));
        return CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
    }
}
