package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @Column(nullable = false, name = "selected_weight")
    private double selectedWeight;

    @Column(nullable = false, name = "selected_layers")
    private double selectedLayers;

    @Column(name = "pieces_number")
    private int piecesNumber;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cake cake;

//    @OneToMany
//    private List<Flavor> flavors;

    @Column(name = "name")
    private String note;

    @Column(name = "fake_layer")
    private int fakeLayer;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItemFlavorTier> orderItemFlavorTiers;

    @Column(name = "user_id")
    private Long userId;

//    @Column(name = "discounted_price")
//    private int discountedPrice;

}
