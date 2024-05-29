package com.aditya_shrikhande.electronic_store.controllers;

import com.aditya_shrikhande.electronic_store.dtos.ImageResponse;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.ProductDto;
import com.aditya_shrikhande.electronic_store.services.FileService;
import com.aditya_shrikhande.electronic_store.services.ProductService;
import com.aditya_shrikhande.electronic_store.dtos.ApiResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    /**
     * Creates a new product.
     *
     * @param productDto The ProductDto containing product details.
     * @return ResponseEntity containing the created ProductDto and HTTP status.
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Creating a new product.");
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    /**
     * Updates an existing product by its ID.
     *
     * @param productId The ID of the product to update.
     * @param productDto The updated ProductDto.
     * @return ResponseEntity containing the updated ProductDto and HTTP status.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        logger.info("Updating product with ID: {}", productId);
        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
        logger.info("Deleting product with ID: {}", productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Product is deleted successfully.")
                .status(HttpStatus.OK)
                .success(true)
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return ResponseEntity containing the retrieved ProductDto and HTTP status.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        logger.info("Retrieving product with ID: {}", productId);
        return new ResponseEntity<>(productService.get(productId), HttpStatus.FOUND);
    }

    /**
     * Retrieves a pageable list of products.
     *
     * @param pageNumber The page number.
     * @param pageSize The page size.
     * @param sortBy The field to sort by.
     * @param sortDir The sorting direction (asc or desc).
     * @return ResponseEntity containing the PageableResponse of ProductDto and HTTP status.
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Retrieving all products.");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * Retrieves a pageable list of live products.
     *
     * @param pageNumber The page number.
     * @param pageSize The page size.
     * @param sortBy The field to sort by.
     * @param sortDir The sorting direction (asc or desc).
     * @return ResponseEntity containing the PageableResponse of ProductDto and HTTP status.
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Retrieving all live products.");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * Searches for products by title.
     *
     * @param query The search query.
     * @param pageNumber The page number.
     * @param pageSize The page size.
     * @param sortBy The field to sort by.
     * @param sortDir The sorting direction (asc or desc).
     * @return ResponseEntity containing the PageableResponse of ProductDto and HTTP status.
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Searching products by title: {}", query);
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    /**
     * Uploads a product image.
     *
     * @param productId The ID of the product to associate with the image.
     * @param image The product image file.
     * @return ResponseEntity containing the ImageResponse and HTTP status.
     * @throws IOException If an error occurs during file upload.
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        logger.info("Uploading product image for product with ID: {}", productId);
        String fileName = fileService.uploadImage(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);

        ImageResponse response = ImageResponse.builder()
                .imageName(updatedProduct.getProductImageName())
                .message("Product image is successfully uploaded.")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Serves a product image by its ID.
     *
     * @param productId The ID of the product to retrieve the image for.
     * @param response The HTTP servlet response.
     * @throws IOException If an error occurs while serving the image.
     */
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
