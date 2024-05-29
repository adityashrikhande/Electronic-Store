package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import org.springframework.http.HttpStatus;

/**
 * A DTO (Data Transfer Object) representing an API response message.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {

    /**
     * The message associated with the API response.
     */
    private String message;

    /**
     * Indicates whether the API operation was successful.
     */
    private boolean success;

    /**
     * The HTTP status code associated with the API response.
     */
    private HttpStatus status;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ApiResponseMessage{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", status=" + status +
                '}';
    }
}
