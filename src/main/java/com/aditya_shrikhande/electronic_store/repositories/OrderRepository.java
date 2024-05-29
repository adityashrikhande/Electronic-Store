package com.aditya_shrikhande.electronic_store.repositories;

import com.aditya_shrikhande.electronic_store.entities.Order;
import com.aditya_shrikhande.electronic_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
