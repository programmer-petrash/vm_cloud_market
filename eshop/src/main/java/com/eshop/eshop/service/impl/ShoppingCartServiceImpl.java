package com.eshop.eshop.service.impl;

import com.eshop.eshop.entity.ShoppingCart;
import com.eshop.eshop.repository.ShoppingCartRepository;
import com.eshop.eshop.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements com.eshop.eshop.service.ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public Iterable<ShoppingCart> getAll() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        if(shoppingCart == null){
            return null;
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart, Long id) {
        ShoppingCart shoppingCartDb = shoppingCartRepository.findById(id).get();
        if(shoppingCartDb == null){
            return null;
        }
        shoppingCartDb.setProducts(shoppingCart.getProducts());
        shoppingCartDb.setUser(shoppingCart.getUser());
        return shoppingCartRepository.save(shoppingCartDb);
    }
}
