package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.andjela.diplomski.entity.codebook.Category;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.andjela.diplomski.entity.codebook.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "title")
    private String title;

    @Column(name = "price_per_kilo")
    private Integer pricePerKilo;

    @Column(name = "decoration_price")
    private Integer decorationPrice;

//    @Column(name = "layer")
//    private Integer layer;
//
//    private Flavor flavor;
//
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @Column(name = "min_weight")
    private Double minWeight;

    @Column(name = "max_weight")
    private Double maxWeight;

    @Column(name = "selected_weight")
    private Double selectedWeight;

    @Column(name = "piece_number")
    private Integer pieceNumber;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)", nullable = false)
    private UUID uuid;
}
