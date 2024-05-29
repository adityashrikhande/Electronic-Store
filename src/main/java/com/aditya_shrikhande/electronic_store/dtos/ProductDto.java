package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import java.util.Date;

/**
 * A DTO (Data Transfer Object) representing a product.
 * This class is used to transfer product-related data between the front-end and back-end.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    /**
     * The unique identifier for the product.
     */
    private String productId;

    /**
     * The title or name of the product.
     */
    private String title;

    /**
     * A description of the product.
     */
    private String description;

    /**
     * The original price of the product.
     */
    private int price;

    /**
     * The discounted price of the product.
     */
    private int discountedPrice;

    /**
     * The available quantity of the product.
     */
    private int quantity;

    /**
     * The date when the product was added.
     */
    private Date addedDate;

    /**
     * Indicates whether the product is live (visible to customers).
     */
    private boolean live;

    /**
     * Indicates whether the product is in stock.
     */
    private boolean stock;

    /**
     * The name of the product's image.
     */
    private String productImageName;

    /**
     * The category to which the product belongs.
     */
    private CategoryDto category;
}
