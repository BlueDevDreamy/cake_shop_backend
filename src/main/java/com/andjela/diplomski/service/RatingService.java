package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.product.ProductMapper;
import com.andjela.diplomski.dto.rating.RatingDto;
import com.andjela.diplomski.dto.rating.RatingMapper;
import com.andjela.diplomski.dto.rating.RatingRequest;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.Product;
import com.andjela.diplomski.entity.Rating;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.repository.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;

    @Override
    //public RatingDto createRating(RatingRequest req, UserDto userDto) {
    public RatingDto createRating(RatingDto ratingDto, UserDto userDto) {
        ProductDto productDto = productService.getProductById(ratingDto.getProductId());
        Product product = ProductMapper.MAPPER.mapToProduct(productDto);
        User user = UserMapper.MAPPER.mapToUser(userDto);
        Rating rating = Rating.builder()
                .product(product)
                .user(user)
                .rating(ratingDto.getRating())
                .createdAt(LocalDateTime.now())
                .build();
        ratingRepository.save(rating);
        return RatingMapper.MAPPER.mapToRatingDto(rating);
    }

    @Override
    public List<RatingDto> getAllRatingsForProduct(Long productId) {
        List<Rating> ratings = ratingRepository.getAllRatingsForProduct(productId);
        return RatingMapper.MAPPER.mapToRatingDtoList(ratings);
    }
}
