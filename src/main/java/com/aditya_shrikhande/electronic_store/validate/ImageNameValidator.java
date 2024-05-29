package com.aditya_shrikhande.electronic_store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Custom validator for the {@link ImageNameValid} annotation.
 */
public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    /**
     * Validates the image name.
     *
     * @param value    The value to be validated (image name).
     * @param context  The validation context.
     * @return {@code true} if the image name is valid, otherwise {@code false}.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Validate if the image name is blank or not
        return !value.isBlank();
    }
}
