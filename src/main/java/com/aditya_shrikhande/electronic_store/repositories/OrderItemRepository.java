package com.aditya_shrikhande.electronic_store.repositories;

import com.aditya_shrikhande.electronic_store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
