package com.andjela.diplomski.service;

import com.andjela.diplomski.dto.rating.RatingDto;
import com.andjela.diplomski.dto.rating.RatingRequest;
import com.andjela.diplomski.dto.user.UserDto;

import java.util.List;

public interface IRatingService {

    //RatingDto createRating(RatingRequest req, UserDto userDto);
    RatingDto createRating(RatingDto ratingDto, UserDto userDto);
    List<RatingDto> getAllRatingsForProduct(Long productId);

}
