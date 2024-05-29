package com.aditya_shrikhande.electronic_store.services;

import com.aditya_shrikhande.electronic_store.dtos.AddItemToCartRequest;
import com.aditya_shrikhande.electronic_store.dtos.CartDto;

/**
 * Service interface for managing a user's shopping cart.
 */
public interface CartService {

    /**
     * Adds an item to the user's shopping cart.
     *
     * @param userId  The ID of the user.
     * @param request The request containing information about the item to add.
     * @return The updated CartDto after adding the item.
     */
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    /**
     * Removes an item from the user's shopping cart.
     *
     * @param userId   The ID of the user.
     * @param cartItem The ID of the cart item to remove.
     */
    void removeItemFromCart(String userId, int cartItem);

    /**
     * Clears the user's shopping cart, removing all items.
     *
     * @param userId The ID of the user.
     */
    void clearCart(String userId);

    /**
     * Retrieves the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return The CartDto representing the user's shopping cart.
     */
    CartDto getCartByUser(String userId);

}

