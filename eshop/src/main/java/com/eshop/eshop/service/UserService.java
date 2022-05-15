package com.eshop.eshop.service;

import com.eshop.eshop.entity.User;

import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    void delete(Long id);
    User updateCart(Long id, User user);
    User updateOrders(Long id, User user);
    User updateUser(Long id, User user);
    void addRole(Long userId, Long roleId);

}
