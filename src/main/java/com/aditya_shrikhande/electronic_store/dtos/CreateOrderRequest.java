package com.aditya_shrikhande.electronic_store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * A DTO (Data Transfer Object) representing a request to create an order.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    /**
     * The ID of the cart associated with the order.
     */
    @NotBlank(message = "Cart ID is required!")
    private String cartId;

    /**
     * The ID of the user placing the order.
     */
    @NotBlank(message = "User ID is required!")
    private String userId;

    /**
     * The order status, which is initially set to "PENDING".
     */
    private String orderStatus = "PENDING";

    /**
     * The payment status, which is initially set to "NOT_PAID".
     */
    private String paymentStatus = "NOT_PAID";

    /**
     * The total order amount.
     */
    private int orderAmount;

    /**
     * The name for billing.
     */
    @NotBlank(message = "Name is required!")
    private String billingName;

    /**
     * The billing address.
     */
    @NotBlank(message = "Address is required!")
    private String billingAddress;

    /**
     * The phone number for billing.
     */
    @NotBlank(message = "Phone is required!")
    private String billingPhone;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "cartId='" + cartId + '\'' +
                ", userId='" + userId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", orderAmount=" + orderAmount +
                ", billingName='" + billingName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", billingPhone='" + billingPhone + '\'' +
                '}';
    }
}
