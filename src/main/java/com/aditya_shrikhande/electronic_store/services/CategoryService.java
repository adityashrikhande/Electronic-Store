package com.aditya_shrikhande.electronic_store.services;

import com.aditya_shrikhande.electronic_store.dtos.CategoryDto;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;

/**
 * Service interface for managing product categories.
 */
public interface CategoryService {

    /**
     * Creates a new product category.
     *
     * @param categoryDto The CategoryDto containing information about the new category.
     * @return The created CategoryDto.
     */
    CategoryDto create(CategoryDto categoryDto);

    /**
     * Updates an existing product category.
     *
     * @param categoryDto The updated CategoryDto.
     * @param categoryId  The ID of the category to update.
     * @return The updated CategoryDto.
     */
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    /**
     * Deletes a product category by its ID.
     *
     * @param categoryId The ID of the category to delete.
     */
    void delete(String categoryId);

    /**
     * Retrieves a paginated list of product categories.
     *
     * @param pageNumber The page number.
     * @param pageSize   The number of categories per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of CategoryDto.
     */
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a product category by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return The CategoryDto representing the category.
     */
    CategoryDto get(String categoryId);

    // You can add a method signature for category searching here if needed.
}
