package com.aditya_shrikhande.electronic_store.repositories;

import com.aditya_shrikhande.electronic_store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
