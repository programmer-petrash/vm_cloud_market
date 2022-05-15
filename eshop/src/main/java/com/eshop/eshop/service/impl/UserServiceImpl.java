package com.eshop.eshop.service.impl;

import com.eshop.eshop.entity.Role;
import com.eshop.eshop.entity.User;
import com.eshop.eshop.repository.RoleRepository;
import com.eshop.eshop.repository.UserRepository;
import com.eshop.eshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registerUser = userRepository.save(user);
        //log.info("IN register - user: {} registered", registerUser);
        return registerUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        //log.info("Get All Users");
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        //log.info("Finded by username {}", username);
        return result;
    }

    @Override
    public User findById(Long id) {

        User result = userRepository.findById(id).orElse(null);
        if(result == null){
            log.warn("No user found with id {}", id);
        }
        //log.info("Finded by id {}", id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        //log.info("Deleted by id {}", id);
    }

    @Override
    public User updateCart(Long id, User user) {
        User userDb = userRepository.findById(id).get();
        if(userDb == null){
            return null;
        }
        userDb.setShoppingCart(user.getShoppingCart());
        userRepository.save(userDb);
        return userDb;
    }

    @Override
    public User updateOrders(Long id, User user) {
        User userDb = userRepository.findById(id).get();
        if(userDb == null) return null;
        userDb.setOrders(user.getOrders());
        return userRepository.save(userDb);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userDb = userRepository.findById(id).get();
        if(userDb == null){
            return null;
        }
        userDb.setUsername(user.getUsername());
        userDb.setPassword(user.getPassword());
        userDb.setShoppingCart(user.getShoppingCart());
        userDb.setRoles(user.getRoles());
        return userRepository.save(userDb);
    }

    @Override
    public void addRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        if(user == null || role == null){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
        user.getRoles().add(role);
        userRepository.saveAndFlush(user);
    }
}
