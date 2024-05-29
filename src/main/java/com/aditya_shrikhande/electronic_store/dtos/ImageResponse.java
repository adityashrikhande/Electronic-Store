package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * A DTO (Data Transfer Object) representing a response for image-related operations.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

    /**
     * The name of the uploaded image.
     */
    private String imageName;

    /**
     * A message describing the result of the image operation.
     */
    private String message;

    /**
     * A flag indicating the success of the image operation.
     */
    private boolean success;

    /**
     * The HTTP status code associated with the response.
     */
    private HttpStatus status;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ImageResponse{" +
                "imageName='" + imageName + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", status=" + status +
                '}';
    }
}
