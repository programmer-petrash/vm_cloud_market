package com.eshop.eshop.service;

import com.eshop.eshop.entity.Order;

public interface OrderService{
    Order add(Order order);
    void delete(Long id);
    Order update(Long id, Order order);
    Order findById(Long id);

}
