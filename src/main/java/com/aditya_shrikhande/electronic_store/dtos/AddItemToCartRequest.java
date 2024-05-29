package com.aditya_shrikhande.electronic_store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO (Data Transfer Object) representing a request to add an item to a cart.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToCartRequest {

    /**
     * The ID of the product to be added to the cart.
     */
    private String productId;

    /**
     * The quantity of the product to be added to the cart.
     */
    private int quantity;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AddItemToCartRequest{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
