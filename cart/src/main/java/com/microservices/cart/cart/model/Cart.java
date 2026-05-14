package com.microservices.cart.cart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @NotNull
    @Column(name = "total_amount")
    private Double totalAmount = 0.0;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.REMOVE})
    List<CartItem> cartItems = new ArrayList<>();

    private Long userId;

    public void setCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

}


