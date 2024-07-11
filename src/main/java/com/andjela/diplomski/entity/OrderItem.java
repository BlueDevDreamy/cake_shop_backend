package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @Column(nullable = false, name = "selected_tiers")
    private double selectedTiers;

    @Column(name = "pieces_number")
    private int piecesNumber;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "cake_id")
    private Cake cake;

    @Column(name = "note")
    private String note;

    @Column(name = "fake_tier")
    private int fakeTier;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItemFlavorTier> orderItemFlavorTiers;

    @Column(name = "user_id")
    private Long userId;

}
