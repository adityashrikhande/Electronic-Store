package com.aditya_shrikhande.electronic_store.repositories;

import com.aditya_shrikhande.electronic_store.entities.Cart;
import com.aditya_shrikhande.electronic_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);
}
