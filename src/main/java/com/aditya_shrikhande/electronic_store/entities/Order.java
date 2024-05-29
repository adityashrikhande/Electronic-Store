package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order entity.
 * This entity stores information about customer orders.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    private String orderId;

    /**
     * The status of the order (e.g., PENDING, DISPATCHED, DELIVERED).
     */
    private String orderStatus;

    /**
     * The payment status of the order (e.g., NOT-PAID, PAID).
     */
    private String paymentStatus;

    /**
     * The total amount of the order.
     */
    private int orderAmount;

    /**
     * The name associated with the billing information for the order.
     */
    private String billingName;

    /**
     * The address associated with the billing information for the order.
     */
    @Column(length = 1000)
    private String billingAddress;

    /**
     * The phone number associated with the billing information for the order.
     */
    private String billingPhone;

    /**
     * The date when the order was placed.
     */
    private Date orderDate;

    /**
     * The date when the order was delivered.
     */
    private Date deliveredDate;

    /**
     * The user who placed the order.
     * This is a many-to-one relationship with the User entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The list of items included in the order.
     * This is a one-to-many relationship with the OrderItem entity.
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}
