package com.aditya_shrikhande.electronic_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user entity.
 * This entity stores information about users of the electronic store.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    private String userId;

    /**
     * The name of the user.
     */
    @Column(name = "user_name")
    private String name;

    /**
     * The email address of the user. It must be unique.
     */
    @Column(name = "user_email", unique = true)
    private String email;

    /**
     * The password associated with the user.
     */
    @Column(name = "user_password", length = 500)
    private String password;

    /**
     * The gender of the user.
     */
    private String gender;

    /**
     * A brief description or about section for the user.
     */
    @Column(length = 1000)
    private String about;

    /**
     * The name of the user's profile image.
     */
    @Column(name = "user_image_name")
    private String imageName;

    /**
     * The list of orders placed by this user.
     * This is a one-to-many relationship with the Order entity.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();


}
