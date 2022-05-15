package com.eshop.eshop.service.impl;

import com.eshop.eshop.entity.Order;
import com.eshop.eshop.repository.OrderRepository;
import com.eshop.eshop.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order add(Order order) {
        if(order == null){
            return null;
        }
        orderRepository.save(order);
        return order;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order update(Long id, Order order) {
        Order orderDb = orderRepository.findById(id).get();
        if(orderDb == null){
            return null;
        }
        orderDb.setOrderStatus(order.getOrderStatus());
        orderDb.setProducts(order.getProducts());
        orderDb.setUser(order.getUser());
        return orderDb;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }
}
