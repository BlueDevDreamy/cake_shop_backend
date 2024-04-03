package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.review.ReviewDto;
import com.andjela.diplomski.dto.review.ReviewRequest;
import com.andjela.diplomski.dto.user.UserDto;

import java.util.List;


public interface IReviewService {
    //ReviewDto createReview(ReviewRequest req, UserDto userDto);
    ReviewDto createReview(ReviewDto reviewDto, UserDto userDto);
    List<ReviewDto> getAllReviewsForProduct(Long productId);
}
