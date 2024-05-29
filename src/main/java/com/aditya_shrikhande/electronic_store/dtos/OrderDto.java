package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO (Data Transfer Object) representing an order.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDto {
    /**
     * The unique identifier for the order.
     */
    private String orderId;

    /**
     * The status of the order (e.g., "PENDING", "COMPLETED").
     */
    private String orderStatus = "PENDING";

    /**
     * The payment status of the order (e.g., "NOT_PAID", "PAID").
     */
    private String paymentStatus = "NOT_PAID";

    /**
     * The total amount of the order.
     */
    private int orderAmount;

    /**
     * The name for billing associated with the order.
     */
    private String billingName;

    /**
     * The billing address for the order.
     */
    private String billingAddress;

    /**
     * The billing phone number for the order.
     */
    private String billingPhone;

    /**
     * The date when the order was created.
     */
    private Date orderDate = new Date();

    /**
     * The date when the order was delivered (if applicable).
     */
    private Date deliveredDate;

    /**
     * The list of order items associated with this order.
     */
    private List<OrderItemDto> orderItems = new ArrayList<>();
}
