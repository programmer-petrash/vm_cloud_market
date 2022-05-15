package com.eshop.eshop.rest;

import com.eshop.eshop.entity.Product;
import com.eshop.eshop.entity.User;
import com.eshop.eshop.service.ProductService;
import com.eshop.eshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/stats/")
public class StatsRestController {
    private final ProductService productService;
    private final UserService userService;

    public StatsRestController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping("top_sale/")
    public ResponseEntity getTopSaleProducts(){
        List<Product> result = new ArrayList<Product>();
        productService.getAll().forEach(result::add);
        result.sort((el, el2)-> el2.getOrders().size() - el.getOrders().size());
        List<String> resultNames = new ArrayList<String>();
        return new ResponseEntity(result.subList(0, result.size() < 5 ? result.size() : 5).stream().map(el->el.getName()), HttpStatus.OK);
    }
    @GetMapping("top_sale_count_users/")
    public ResponseEntity getTopSaleCountUsers(){
        List<User> result = new ArrayList<User>();
        result.addAll(userService.getAll());
        result.sort((el, el2)-> el2.getOrders().size() - el.getOrders().size());
        List<String> resultNames = new ArrayList<String>();
        return new ResponseEntity(result.subList(0, result.size() < 5 ? result.size() : 5).stream().map(el->el.getUsername()), HttpStatus.OK);
    }
    /*@GetMapping("top_sale_sum_users/")
    public ResponseEntity getTopSaleSummUsers(){
        List<User> result = new ArrayList<User>();
        result.addAll(userService.getAll());
        result.sort((el, el2)-> el2.getOrders().stream().map(o -> o.getProducts()).collect(Collectors.toList())));
        List<String> resultNames = new ArrayList<String>();
        return new ResponseEntity(result.subList(0, result.size() < 5 ? result.size() : 5).stream().map(el->el.getUsername()), HttpStatus.OK);
    } */

}
