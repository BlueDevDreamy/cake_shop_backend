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
import com.andjela.diplomski.repository.CartRepository;
import com.andjela.diplomski.request.AddItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

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
    public String addCartItem(Long userId, AddItemRequest req) {
        Cart cart = cartRepository.findUserById(userId);
        CartDto cartDto = CartMapper.MAPPER.mapToCartDto(cart);
        ProductDto productDto = productService.getProductById(req.getProductId());
        Product product = ProductMapper.MAPPER.mapToProduct(productDto);
        CartItemDto cartItemDto = cartItemService.isCartItemExists(cartDto, productDto, req.getWeight(), userId);
        if(cartItemDto == null){
            Integer price = req.getQuantity() * product.getDiscountedPrice();
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(req.getQuantity())
                    .userId(userId)
                    .price(price)
                    .weight(req.getWeight())
                    .build();
            CartItemDto createdCartItemDto = CartItemMapper.MAPPER.mapToCartItemDto(cartItem);
            cartItemService.createCartItem(createdCartItemDto);
            cart.getCartItems().add(CartItemMapper.MAPPER.mapToCartItem(createdCartItemDto));
        }
        return "Item added to cart";
    }

    @Override
    public CartDto getUserCart(Long userId) {
        Cart cart = cartRepository.findUserById(userId);
        Integer totalPrice = 0;
        Integer totalDiscountedPrice = 0;
        int totalItem = 0;
        for(CartItem cartItem : cart.getCartItems()){
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cart.getTotalDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);
        cartRepository.save(cart);
        return CartMapper.MAPPER.mapToCartDto(cart);
    }
}
