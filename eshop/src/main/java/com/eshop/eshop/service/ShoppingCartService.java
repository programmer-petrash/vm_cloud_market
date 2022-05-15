package com.eshop.eshop.service;

import com.eshop.eshop.entity.Product;
import com.eshop.eshop.entity.ShoppingCart;

public interface ShoppingCartService {
    Iterable<ShoppingCart> getAll();
    ShoppingCart findById(Long id);
    void delete(Long id);
    ShoppingCart add(ShoppingCart shoppingCart);
    ShoppingCart update(ShoppingCart shoppingCart, Long id);
}
