package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
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

//    @GeneratedValue(strategy = GenerationType.UUID)
//    @UuidGenerator
//    @JdbcTypeCode(SqlTypes.CHAR)
//    @Column(columnDefinition = "char(36)", nullable = false)
//    private UUID uuid;
}
