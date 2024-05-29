package com.aditya_shrikhande.electronic_store.controllers;

import com.aditya_shrikhande.electronic_store.dtos.ApiResponseMessage;
import com.aditya_shrikhande.electronic_store.dtos.CreateOrderRequest;
import com.aditya_shrikhande.electronic_store.dtos.OrderDto;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.services.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param request The CreateOrderRequest containing order details.
     * @return ResponseEntity containing the created OrderDto and HTTP status.
     */
    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        logger.info("Creating a new order.");
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * Removes an order by its ID.
     *
     * @param orderId The ID of the order to be removed.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId) {
        logger.info("Removing order with ID: {}", orderId);
        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .success(true)
                .message("Order is removed.")
                .build();

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    /**
     * Retrieves orders of a user by user ID.
     *
     * @param userId The ID of the user to retrieve orders for.
     * @return ResponseEntity containing a list of OrderDto and HTTP status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        logger.info("Retrieving orders of user with ID: {}", userId);
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    /**
     * Retrieves a pageable list of orders.
     *
     * @param pageNumber The page number to retrieve (default is 0).
     * @param pageSize The number of orders per page (default is 10).
     * @param sortBy The field to sort orders by (default is "orderDate").
     * @param sortDir The direction of sorting ("asc" or "desc", default is "desc").
     * @return ResponseEntity containing a PageableResponse of OrderDto objects and HTTP status.
     */
    @GetMapping()
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        logger.info("Retrieving orders - Page: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
