package com.eshop.eshop.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Float price;

    @ManyToMany(mappedBy = "products")
    private List<ShoppingCart> shopping_carts;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;


}
