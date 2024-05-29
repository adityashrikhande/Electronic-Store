package com.aditya_shrikhande.electronic_store.dtos;

import com.aditya_shrikhande.electronic_store.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * A DTO (Data Transfer Object) representing a user.
 * This class is used to transfer user-related data between the front-end and back-end.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    /**
     * The unique identifier for the user.
     */
    private String userId;

    /**
     * The name of the user.
     * Should be between 3 and 20 characters.
     */
    @Size(min = 3, max = 20, message = "Invalid Name !!!")
    private String name;

    /**
     * The email address of the user.
     * Should be a valid email format.
     */
    @Email(message = "Invalid Email !!!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9.-]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid user email !!!")
    @NotBlank(message = "Email is required !!!")
    private String email;

    /**
     * The password associated with the user.
     * Should not be blank.
     */
    @NotBlank(message = "Password is required !!!")
    private String password;

    /**
     * The gender of the user.
     * Should be between 4 and 6 characters.
     */
    @Size(min = 4, max = 6, message = "Invalid Gender !!!")
    private String gender;

    /**
     * A brief description about the user.
     * Should not be blank.
     */
    @NotBlank(message = "Write something about yourself !!!")
    private String about;

    /**
     * The name of the user's image.
     * This field is validated using a custom validator.
     */
    @ImageNameValid // Custom validator that is created manually
    private String imageName;
}
