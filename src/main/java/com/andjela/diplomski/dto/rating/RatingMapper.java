package com.andjela.diplomski.dto.rating;

import com.andjela.diplomski.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RatingMapper {
    RatingMapper MAPPER = Mappers.getMapper(RatingMapper.class);

    RatingDto mapToRatingDto(Rating rating);

    List<RatingDto> mapToRatingDtoList(List<Rating> ratings);
    Rating mapToRating(RatingDto ratingDto);
}
