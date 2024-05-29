package com.aditya_shrikhande.electronic_store.services;

import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.ProductDto;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param productDto The ProductDto containing information about the new product.
     * @return The created ProductDto.
     */
    ProductDto create(ProductDto productDto);

    /**
     * Updates an existing product.
     *
     * @param productDto The updated ProductDto.
     * @param productId  The ID of the product to update.
     * @return The updated ProductDto.
     */
    ProductDto update(ProductDto productDto, String productId);

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    void delete(String productId);

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The ProductDto representing the product.
     */
    ProductDto get(String productId);

    /**
     * Retrieves a paginated list of all products.
     *
     * @param pageNumber The page number.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of ProductDto.
     */
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a paginated list of live products.
     *
     * @param pageNumber The page number.
     * @param pageSize   The number of live products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of live ProductDto.
     */
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Searches for products by title.
     *
     * @param subTitle   The title keyword to search for.
     * @param pageNumber The page number.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of ProductDto matching the search criteria.
     */
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Creates a new product with a specified category.
     *
     * @param productDto The ProductDto containing information about the new product.
     * @param categoryId The ID of the category to associate with the product.
     * @return The created ProductDto with the specified category.
     */
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    /**
     * Updates the category of a product.
     *
     * @param productId  The ID of the product to update.
     * @param categoryId The ID of the new category to associate with the product.
     * @return The updated ProductDto with the new category.
     */
    ProductDto updateCategory(String productId, String categoryId);

    /**
     * Retrieves a paginated list of products belonging to a specific category.
     *
     * @param categoryId The ID of the category.
     * @param pageNumber The page number.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of ProductDto within the specified category.
     */
    PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);

    // You can add more product-related methods here if needed.
}
