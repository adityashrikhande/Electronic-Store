package com.aditya_shrikhande.electronic_store.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception class for representing a resource not found exception.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);

    /**
     * Constructs a new ResourceNotFoundException with a default message.
     */
    public ResourceNotFoundException() {
        super("Resource Not Found!!!");
        logger.error("ResourceNotFoundException: Resource Not Found!!!");
    }

    /**
     * Constructs a new ResourceNotFoundException with a custom message.
     *
     * @param message The custom error message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
        logger.error("ResourceNotFoundException: {}", message);
    }
}
