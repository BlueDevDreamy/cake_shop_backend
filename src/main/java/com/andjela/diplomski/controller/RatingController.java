package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.rating.RatingDto;
import com.andjela.diplomski.dto.user.UserDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.RatingService;
import com.andjela.diplomski.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final UserService userService;
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingDto> createRatingHandler(@RequestBody RatingDto req, @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        RatingDto ratingDto = ratingService.createRating(req, UserMapper.MAPPER.mapToUserDTO(user));
        return new ResponseEntity<>(ratingDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RatingDto>> getProductsReviewHandler(@PathVariable Long productId) {
        List<RatingDto> ratings = ratingService.getAllRatingsForProduct(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
