package com.andjela.diplomski.controller;

import com.andjela.diplomski.dto.review.ReviewDto;
import com.andjela.diplomski.dto.user.UserMapper;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.service.ReviewService;
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
@RequestMapping("/api/reviews")
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReviewHandler(@RequestBody ReviewDto req, @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserByJwt(jwt);
        System.out.println("product id " + req.getProductId() + " - " + req.getReview());
        ReviewDto reviewDto = reviewService.createReview(req, UserMapper.MAPPER.mapToUserDTO(user));
        System.out.println("product review " + req.getReview());
        return new ResponseEntity<>(reviewDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getProductsReviewHandler(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsForProduct(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
