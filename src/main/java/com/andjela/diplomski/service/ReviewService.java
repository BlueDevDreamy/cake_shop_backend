package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.product.ProductDto;
import com.andjela.diplomski.dto.product.ProductMapper;
import com.andjela.diplomski.dto.review.ReviewDto;
import com.andjela.diplomski.dto.review.ReviewMapper;
import com.andjela.diplomski.dto.review.ReviewRequest;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.Product;
import com.andjela.diplomski.entity.Review;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    @Override
    //public ReviewDto createReview(ReviewRequest req, UserDto userDto) {
    public ReviewDto createReview(ReviewDto reviewDto, UserDto userDto) {
        ProductDto productDto = productService.getProductById(reviewDto.getProductId());
        Product product = ProductMapper.MAPPER.mapToProduct(productDto);
        User user = UserMapper.MAPPER.mapToUser(userDto);
        Review review = Review.builder()
                .product(product)
                .user(user)
                .review(reviewDto.getReview())
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);
        return ReviewMapper.MAPPER.mapToReviewDto(review);
    }

    @Override
    public List<ReviewDto> getAllReviewsForProduct(Long productId) {
        List<Review> reviews = reviewRepository.getAllReviewsForProduct(productId);
        return ReviewMapper.MAPPER.mapToReviewDtoList(reviews);
    }
}
