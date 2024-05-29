package com.aditya_shrikhande.electronic_store.repositories;

import com.aditya_shrikhande.electronic_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Searches for users whose name contains the specified keyword.
     *
     * @param keyword The keyword to search for in usernames.
     * @return A list of users whose names contain the keyword.
     */
    List<User> findByNameContaining(String keyword);




    //    @Query("SELECT u FROM users u " +
//            "INNER JOIN FETCH u.cart c " +
//            "INNER JOIN FETCH c.cart_items ci " +
//            "INNER JOIN FETCH ci.products p " +
//            "WHERE u.userEmail = :email")
//    User findUserWithCartItemsAndProducts(@Param("email") String email);
}
