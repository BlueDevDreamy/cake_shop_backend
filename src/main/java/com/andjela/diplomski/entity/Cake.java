package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.andjela.diplomski.entity.codebook.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cake")
public class Cake extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price_per_kilo", nullable = false)
    private int pricePerKilo;

    @Column(name = "decoration_price")
    private int decorationPrice;

    @Column(name = "min_weight")
    private double minWeight;

    @Column(name = "max_weight")
    private double maxWeight;

    @Column(name = "min_tier")
    private int minTier;

    @Column(name = "max_tier")
    private int maxTier;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Category category;

}
