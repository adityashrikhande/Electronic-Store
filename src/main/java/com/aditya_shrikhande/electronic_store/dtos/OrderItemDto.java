package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

/**
 * A DTO (Data Transfer Object) representing an item within an order.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {
    /**
     * The unique identifier for the order item.
     */
    private int orderItemId;

    /**
     * The quantity of the product in the order item.
     */
    private int quantity;

    /**
     * The total price of the order item.
     */
    private int totalPrice;

    /**
     * The product associated with this order item.
     */
    private ProductDto product;
}
