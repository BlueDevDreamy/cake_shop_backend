package com.andjela.diplomski.dto.product;

import com.andjela.diplomski.dto.category.CategoryDto;
import com.andjela.diplomski.dto.flavor.FlavorDto;
import com.andjela.diplomski.dto.rating.RatingDto;
import com.andjela.diplomski.dto.review.ReviewDto;
import com.andjela.diplomski.entity.Rating;
import com.andjela.diplomski.entity.Review;
import com.andjela.diplomski.entity.codebook.Category;
import com.andjela.diplomski.entity.codebook.Flavor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String title;

    private String description;

    private Integer price;

    private Integer discountedPrice;

    private Integer discountedPercent;

    private Set<FlavorDto> flavors;

    private Double weight;

    private Integer decorationPrice;

    private String topLevelCategory;

    private String secondLevelCategory;

    private String imageUrl;

    private List<RatingDto> ratings;

    private List<ReviewDto> reviews;

    private int ratingCount;
}
