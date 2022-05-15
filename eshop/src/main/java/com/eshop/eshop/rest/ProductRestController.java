package com.eshop.eshop.rest;

import com.eshop.eshop.dto.ProductDto;
import com.eshop.eshop.entity.Product;
import com.eshop.eshop.service.ProductService;
import liquibase.pro.packaged.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products/")
public class ProductRestController {
    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("add/")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        if(product == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        productService.add(product);
        return new ResponseEntity(product, HttpStatus.CREATED);
    }
    @GetMapping(value = "get/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") Long id){
        Product product = productService.findById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ProductDto.toProductDto(product), HttpStatus.OK);
    }
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity deleteProductById(@PathVariable(name = "id") Long id){
        if(productService.findById(id) == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<Iterable<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
    @PostMapping("update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto newProduct){
        Product product = productService.updateProduct(ProductDto.toProduct(newProduct), id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ProductDto.toProductDto(product), HttpStatus.OK);
    }
}
