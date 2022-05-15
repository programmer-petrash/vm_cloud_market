package com.eshop.eshop.repository;

import com.eshop.eshop.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
