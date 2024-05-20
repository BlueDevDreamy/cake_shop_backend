package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import com.andjela.diplomski.entity.codebook.Flavor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;
@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
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

    @JsonIgnore
    @ManyToOne
    private Cake cake;

    @OneToMany
    private List<Flavor> flavors;

    @Column(name = "name")
    private String note;

    @Column(name = "fake_tier")
    private int fakeTier;

    @JsonIgnore
    @ManyToOne ()
    private Cart cart;

    @Column(name = "user_id")
    private Long userId;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", selectedWeight=" + selectedWeight +
                ", selectedTiers=" + selectedTiers +
                ", piecesNumber=" + piecesNumber +
                ", totalPrice=" + totalPrice +
                ", cake=" + cake +
                ", flavors=" + flavors +
                ", note='" + note + '\'' +
                ", fakeTier=" + fakeTier +
                ", cart=" + cart +
                ", userId=" + userId +
                '}';
    }
}
