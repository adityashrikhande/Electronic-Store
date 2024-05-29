package com.aditya_shrikhande.electronic_store.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an item in a shopping cart entity.
 * This entity stores information about a product in a user's cart, including quantity and total price.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {

    /**
     * The unique identifier for the cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    /**
     * The quantity of the product in the cart item.
     */
    private int quantity;

    /**
     * The total price of the cart item.
     */
    private int totalPrice;

    /**
     * The product associated with the cart item.
     * This is a one-to-one relationship with the Product entity.
     */
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The cart to which this item belongs.
     * This is a many-to-one relationship with the Cart entity.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
