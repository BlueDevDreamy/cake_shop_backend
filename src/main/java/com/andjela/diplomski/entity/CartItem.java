package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.fasterxml.jackson.annotation.*;
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
@Table(name = "cart_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "selected_weight")
    private double selectedWeight;

    @Column(nullable = false, name = "selected_tiers")
    private int selectedTiers;

    @Column(name = "pieces_number")
    private int piecesNumber;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "cake_id")
    private Cake cake;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItemFlavorTier> cartItemFlavorTiers;

    @Column(name = "note")
    private String note;

    @Column(name = "fake_tier")
    private int fakeTier;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonIgnore
    private Cart cart;

    @Column(name = "user_id")
    private Long userId;

}
