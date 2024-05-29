package com.aditya_shrikhande.electronic_store.services;

import com.aditya_shrikhande.electronic_store.dtos.CreateOrderRequest;
import com.aditya_shrikhande.electronic_store.dtos.OrderDto;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;

import java.util.List;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Creates a new order.
     *
     * @param orderDto The CreateOrderRequest containing information about the new order.
     * @return The created OrderDto.
     */
    OrderDto createOrder(CreateOrderRequest orderDto);

    /**
     * Removes an order by its ID.
     *
     * @param orderId The ID of the order to remove.
     */
    void removeOrder(String orderId);

    /**
     * Retrieves a list of orders placed by a user.
     *
     * @param userId The ID of the user.
     * @return A list of OrderDto representing the user's orders.
     */
    List<OrderDto> getOrdersOfUser(String userId);

    /**
     * Retrieves a paginated list of orders.
     *
     * @param pageNumber The page number.
     * @param pageSize   The number of orders per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sorting direction ("asc" for ascending, "desc" for descending).
     * @return A PageableResponse containing a list of OrderDto.
     */
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    // You can add more order-related methods here if needed.
}
