package com.aditya_shrikhande.electronic_store.services.impl;

import com.aditya_shrikhande.electronic_store.exceptions.ResourceNotFoundException;
import com.aditya_shrikhande.electronic_store.repositories.CategoryRepository;
import com.aditya_shrikhande.electronic_store.repositories.ProductRepository;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.ProductDto;
import com.aditya_shrikhande.electronic_store.entities.Category;
import com.aditya_shrikhande.electronic_store.entities.Product;
import com.aditya_shrikhande.electronic_store.helper.Helper;
import com.aditya_shrikhande.electronic_store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    /**
     * Create a new product based on the provided product details.
     *
     * @param productDto The ProductDto containing product details.
     * @return The created ProductDto.
     */
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);

        // Generate a unique product ID
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        // Set the added date to the current date
        product.setAddedDate(new Date());

        // Save the product to the database
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    /**
     * Update a product based on the provided product details and product ID.
     *
     * @param productDto The ProductDto containing updated product details.
     * @param productId  The ID of the product to update.
     * @return The updated ProductDto.
     */
    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given Id !!!"));

        // Update product fields based on the provided DTO
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setAddedDate(productDto.getAddedDate());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        // Save the updated product to the database
        Product updatedProduct = productRepository.save(product);

        return mapper.map(updatedProduct, ProductDto.class);
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId The ID of the product to delete.
     */
    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given Id !!!"));

        // Delete the product from the database
        productRepository.delete(product);
    }

    /**
     * Get a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The ProductDto for the retrieved product.
     */
    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given Id !!!"));

        return mapper.map(product, ProductDto.class);
    }

    /**
     * Get a paginated list of all products.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of ProductDto objects.
     */
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Define sorting criteria based on sort direction and field
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());

        // Create a pageable request with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Retrieve a page of products from the database
        Page<Product> page = productRepository.findAll(pageable);

        // Convert the Page<Product> to a PageableResponse<ProductDto>
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    /**
     * Get a paginated list of all live products.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of live ProductDto objects.
     */
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Define sorting criteria based on sort direction and field
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());

        // Create a pageable request with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Retrieve a page of live products from the database
        Page<Product> page = productRepository.findByLiveTrue(pageable);

        // Convert the Page<Product> to a PageableResponse<ProductDto>
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    /**
     * Search for products by title containing a specified substring.
     *
     * @param subTitle   The substring to search for in product titles.
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of ProductDto objects that match the search criteria.
     */
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Define sorting criteria based on sort direction and field
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());

        // Create a pageable request with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Search for products by title containing the specified substring
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);

        // Convert the Page<Product> to a PageableResponse<ProductDto>
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    /**
     * Create a new product with a specified category.
     *
     * @param productDto The ProductDto containing product details.
     * @param categoryId The ID of the category to associate with the product.
     * @return The created ProductDto with the associated category.
     */
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        // Fetch the category from the database based on the provided categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given Id !!!"));

        Product product = mapper.map(productDto, Product.class);

        // Generate a unique product ID
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);

        // Set the added date to the current date
        product.setAddedDate(new Date());

        // Associate the product with the specified category
        product.setCategory(category);

        // Save the product to the database
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    /**
     * Update the category of a product.
     *
     * @param productId  The ID of the product to update.
     * @param categoryId The ID of the new category to associate with the product.
     * @return The updated ProductDto with the new category.
     */
    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        // Fetch the product based on the provided productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with given Id !!!"));

        // Fetch the category based on the provided categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(("Category not found with given ID")));

        // Update the product's category
        product.setCategory(category);

        // Save the updated product to the database
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    /**
     * Get a paginated list of products belonging to a specific category.
     *
     * @param categoryId The ID of the category to filter products by.
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of products per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of ProductDto objects belonging to the specified category.
     */
    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Fetch the category based on the provided categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(("Category not found with given ID")));

        // Define sorting criteria based on sort direction and field
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());

        // Create a pageable request with page number, page size, and sorting
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Retrieve a page of products belonging to the specified category
        Page<Product> page = productRepository.findByCategory(category, pageable);

        // Convert the Page<Product> to a PageableResponse<ProductDto>
        return Helper.getPageableResponse(page, ProductDto.class);
    }

}
