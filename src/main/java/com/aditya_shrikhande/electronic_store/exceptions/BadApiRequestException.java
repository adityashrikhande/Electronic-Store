package com.aditya_shrikhande.electronic_store.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception class for representing a bad API request.
 */
public class BadApiRequestException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(BadApiRequestException.class);

    /**
     * Constructs a new BadApiRequestException with a default message.
     * The default message is "Bad Request !!!".
     */
    public BadApiRequestException() {
        super("Bad Request !!!");
        logger.error("BadApiRequestException: Bad Request !!!");
    }

    /**
     * Constructs a new BadApiRequestException with a custom message.
     *
     * @param message The custom error message.
     */
    public BadApiRequestException(String message) {
        super(message);
        logger.error("BadApiRequestException: {}", message);
    }
}
