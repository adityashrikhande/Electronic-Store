package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an order item entity.
 * This entity stores information about individual items within an order.
 */
@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {

    /**
     * The unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    /**
     * The quantity of this item included in the order.
     */
    private int quantity;

    /**
     * The total price of this order item.
     */
    private int totalPrice;

    /**
     * The product associated with this order item.
     * This is a one-to-one relationship with the Product entity.
     */
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The order to which this item belongs.
     * This is a many-to-one relationship with the Order entity.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
