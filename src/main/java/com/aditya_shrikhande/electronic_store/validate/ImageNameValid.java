package com.aditya_shrikhande.electronic_store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation for validating image names.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    /**
     * Default error message to be used when validation fails.
     *
     * @return The default error message.
     */
    String message() default "Invalid Image Name";

    /**
     * Represents a group of constraints that this constraint belongs to.
     *
     * @return The groups of constraints.
     */
    Class<?>[] groups() default {};

    /**
     * Additional payload about the validation.
     *
     * @return The payload information.
     */
    Class<? extends Payload>[] payload() default {};
}
