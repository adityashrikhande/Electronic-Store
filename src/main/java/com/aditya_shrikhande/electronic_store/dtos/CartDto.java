package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DTO (Data Transfer Object) representing a shopping cart.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    /**
     * The unique identifier of the shopping cart.
     */
    private String cartId;

    /**
     * The date and time when the shopping cart was created.
     */
    private Date createdAt;

    /**
     * The user associated with the shopping cart.
     */
    private UserDto user;

    /**
     * The list of cart items in the shopping cart.
     */
    private List<CartItemDto> items = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CartDto{" +
                "cartId='" + cartId + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", items=" + items +
                '}';
    }
}
