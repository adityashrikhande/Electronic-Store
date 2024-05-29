package com.aditya_shrikhande.electronic_store.controllers;

import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.ProductDto;
import com.aditya_shrikhande.electronic_store.services.CategoryService;
import com.aditya_shrikhande.electronic_store.services.ProductService;
import com.aditya_shrikhande.electronic_store.dtos.ApiResponseMessage;
import com.aditya_shrikhande.electronic_store.dtos.CategoryDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * Creates a new category.
     *
     * @param categoryDto The CategoryDto containing category details.
     * @return ResponseEntity containing the created CategoryDto and HTTP status.
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Creating a new category: {}", categoryDto.getTitle());
        CategoryDto createdCategory = categoryService.create(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Updates an existing category by its ID.
     *
     * @param categoryId The ID of the category to be updated.
     * @param categoryDto The CategoryDto containing updated category details.
     * @return ResponseEntity containing the updated CategoryDto and HTTP status.
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto) {
        logger.info("Updating category with ID: {}", categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId The ID of the category to be deleted.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Category is deleted successfully.")
                .status(HttpStatus.OK)
                .success(true)
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    /**
     * Retrieves a pageable list of all categories.
     *
     * @param pageNumber The page number to retrieve (default is 0).
     * @param pageSize The number of categories per page (default is 10).
     * @param sortBy The field to sort categories by (default is "title").
     * @param sortDir The direction of sorting ("asc" or "desc", default is "asc").
     * @return ResponseEntity containing a PageableResponse of CategoryDto objects and HTTP status.
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Retrieving all categories - Page: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return ResponseEntity containing the CategoryDto and HTTP status.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        logger.info("Retrieving category by ID: {}", categoryId);
        return new ResponseEntity<>(categoryService.get(categoryId), HttpStatus.FOUND);
    }

    /**
     * Creates a new product with the specified category.
     *
     * @param categoryId The ID of the category for the product.
     * @param productDto The ProductDto containing product details.
     * @return ResponseEntity containing the created ProductDto and HTTP status.
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ) {
        logger.info("Creating a new product with category ID: {}", categoryId);
        return new ResponseEntity<>(productService.createWithCategory(productDto, categoryId), HttpStatus.CREATED);
    }

    /**
     * Updates the category of a product.
     *
     * @param categoryId The new category ID for the product.
     * @param productId The ID of the product to update.
     * @return ResponseEntity containing the updated ProductDto and HTTP status.
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        logger.info("Updating category of product with ID: {} to new category ID: {}", productId, categoryId);
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * Retrieves a pageable list of products within a category.
     *
     * @param pageNumber The page number to retrieve (default is 0).
     * @param pageSize The number of products per page (default is 10).
     * @param sortBy The field to sort products by (default is "title").
     * @param sortDir The direction of sorting ("asc" or "desc", default is "asc").
     * @param categoryId The ID of the category to filter products by.
     * @return ResponseEntity containing a PageableResponse of ProductDto objects and HTTP status.
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable String categoryId
    ) {
        logger.info("Retrieving products of category with ID: {} - Page: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                categoryId, pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
