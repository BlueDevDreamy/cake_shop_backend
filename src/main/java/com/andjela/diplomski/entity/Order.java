package com.andjela.diplomski.entity;

import com.andjela.diplomski.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

//    @OneToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "shipping_address_id", unique = false)
    private Address shippingAddress;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "total_item")
    private int totalItem;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;
}
