package com.aditya_shrikhande.electronic_store.controllers;

import com.aditya_shrikhande.electronic_store.dtos.AddItemToCartRequest;
import com.aditya_shrikhande.electronic_store.dtos.ApiResponseMessage;
import com.aditya_shrikhande.electronic_store.dtos.CartDto;
import com.aditya_shrikhande.electronic_store.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    /**
     * Adds items to a user's cart.
     *
     * @param userId  The ID of the user.
     * @param request The request containing item details to be added.
     * @return ResponseEntity containing the updated CartDto and HTTP status.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(
            @PathVariable String userId,
            @RequestBody AddItemToCartRequest request) {

        logger.info("Adding items to cart for user ID: {}", userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /**
     * Removes an item from a user's cart.
     *
     * @param userId The ID of the user.
     * @param itemId The ID of the item to be removed.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable int itemId) {

        logger.info("Removing item with ID {} from cart for user ID: {}", itemId, userId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Item is removed from the cart.")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Clears a user's entire cart.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(
            @PathVariable String userId) {

        logger.info("Clearing cart for user ID: {}", userId);
        cartService.clearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder()
                .message("Cart is cleared.")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves a user's cart.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the CartDto and HTTP status.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable String userId) {

        logger.info("Retrieving cart for user ID: {}", userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
