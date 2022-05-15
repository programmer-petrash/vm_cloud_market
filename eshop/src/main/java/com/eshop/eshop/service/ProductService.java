package com.eshop.eshop.service;

import com.eshop.eshop.entity.Product;
import com.eshop.eshop.entity.User;

import java.util.List;

public interface ProductService {
    Iterable<Product> getAll();
    Product findById(Long id);
    void delete(Long id);
    void add(Product product);
    Product updateProduct(Product product, Long id);
}
