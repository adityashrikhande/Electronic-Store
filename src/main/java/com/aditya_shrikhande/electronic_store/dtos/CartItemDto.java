package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

/**
 * A DTO (Data Transfer Object) representing an item in a shopping cart.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    /**
     * The unique identifier of the cart item.
     */
    private int cartItemId;

    /**
     * The product associated with the cart item.
     */
    private ProductDto product;

    /**
     * The quantity of the product in the cart item.
     */
    private int quantity;

    /**
     * The total price of the cart item (price per unit multiplied by quantity).
     */
    private int totalPrice;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CartItemDto{" +
                "cartItemId=" + cartItemId +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
