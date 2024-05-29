package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a user's shopping cart entity.
 * This entity stores information about the user's cart, including items and creation date.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "cart")
public class Cart {

    /**
     * The unique identifier for the cart.
     */
    @Id
    private String cartId;

    /**
     * The date when the cart was created.
     */
    private Date createdAt;

    /**
     * The user associated with the cart.
     * This is a one-to-one and unidirectional relationship with the User entity.
     */
    @OneToOne
    private User user;

    /**
     * The list of items in the cart.
     * This is a one-to-many relationship with the CartItem entity.
     * CascadeType.ALL ensures that operations on Cart cascade to CartItem.
     * Orphan removal is enabled, meaning CartItems are removed if they are removed from this list.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
}
