package com.eshop.eshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity{
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    private OrderStatus orderStatus;
    @ManyToMany()
    @JoinTable(name = "orders_products", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    List<Product> products;
}
