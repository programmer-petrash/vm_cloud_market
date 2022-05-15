package com.eshop.eshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Data
public class ShoppingCart extends BaseEntity{
    @ManyToMany
    @JoinTable(name = "shopping_cart_products", joinColumns = {@JoinColumn(name = "shopping_cart_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    private List<Product> products;

    //@OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @OneToOne(mappedBy = "shoppingCart", fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
