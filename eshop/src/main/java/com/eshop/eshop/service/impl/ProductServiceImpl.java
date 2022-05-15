package com.eshop.eshop.service.impl;

import com.eshop.eshop.entity.Product;
import com.eshop.eshop.repository.ProductRepository;
import com.eshop.eshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getAll() {
        //log.info("Get All Products");
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void add(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product productDb
                = productRepository.findById(id)
                .get();

        if (Objects.nonNull(product.getName())
                && !"".equalsIgnoreCase(
                product.getName())) {
            productDb.setName(
                    product.getName());
        }

        if (Objects.nonNull(
                product.getDescription())
                && !"".equalsIgnoreCase(
                product.getDescription())) {
            productDb.setDescription(
                    product.getDescription());
        }

        productDb.setPrice(product.getPrice());
        return productRepository.save(productDb);
    }
}
