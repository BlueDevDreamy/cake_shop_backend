package com.andjela.diplomski.dto.review;

import com.andjela.diplomski.dto.rating.RatingDto;
import com.andjela.diplomski.dto.rating.RatingMapper;
import com.andjela.diplomski.entity.Rating;
import com.andjela.diplomski.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper(ReviewMapper.class);

    ReviewDto mapToReviewDto(Review review);
    List<ReviewDto> mapToReviewDtoList(List<Review> reviews);
    Review mapToReview(ReviewDto reviewDto);
}
