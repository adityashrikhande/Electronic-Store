package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category entity.
 * This entity stores information about product categories.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    /**
     * The unique identifier for the category.
     */
    @Id
    private String categoryId;

    /**
     * The title of the category.
     */
    @Column(name = "category_title", length = 60, nullable = false)
    private String title;

    /**
     * The description of the category.
     */
    @Column(name = "category_desc", length = 50)
    private String description;

    /**
     * The cover image associated with the category.
     */
    private String coverImage;

    /**
     * The list of products that belong to this category.
     * This is a one-to-many relationship with the Product entity.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
