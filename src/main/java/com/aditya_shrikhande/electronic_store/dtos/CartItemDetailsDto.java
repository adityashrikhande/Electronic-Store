package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDetailsDto {

    private int cartItemId;
    private int quantity;
    private int totalPrice;
    private Date createdAt;
    private ProductDto product;
    private UserDto user;
}
