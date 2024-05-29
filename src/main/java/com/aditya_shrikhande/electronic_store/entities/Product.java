package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Represents a product entity.
 * This entity stores information about products available in the electronic store.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    /**
     * The unique identifier for the product.
     */
    @Id
    private String productId;

    /**
     * The title or name of the product.
     */
    private String title;

    /**
     * The description or details of the product.
     */
    @Column(length = 10000)
    private String description;

    /**
     * The price of the product.
     */
    private int price;

    /**
     * The discounted price of the product, if applicable.
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
     * Indicates if the product is live or active.
     */
    private boolean live;

    /**
     * Indicates if the product is in stock.
     */
    private boolean stock;

    /**
     * The name of the product's image file.
     */
    private String productImageName;

    /**
     * The category to which this product belongs.
     * This is a many-to-one relationship with the Category entity.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
