package com.aditya_shrikhande.electronic_store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * A DTO (Data Transfer Object) representing a product category.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    /**
     * The unique identifier of the category.
     */
    private String categoryId;

    /**
     * The title of the category.
     */
    @NotBlank(message = "Title is required !!!")
    @Size(min = 4, message = "Title must be at least 4 characters long !!!")
    private String title;

    /**
     * The description of the category.
     */
    @NotBlank(message = "Description is required !!!")
    private String description;

    /**
     * The URL of the cover image for the category.
     */
    @NotBlank(message = "Cover Image URL is required !!!")
    private String coverImage;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CategoryDto{" +
                "categoryId='" + categoryId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coverImage='" + coverImage + '\'' +
                '}';
    }
}
