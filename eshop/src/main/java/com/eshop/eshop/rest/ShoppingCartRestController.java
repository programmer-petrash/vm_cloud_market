package com.eshop.eshop.rest;

import com.eshop.eshop.dto.ShoppingCartDto;
import com.eshop.eshop.dto.ShoppingCartResponseDto;
import com.eshop.eshop.entity.*;
import com.eshop.eshop.service.OrderService;
import com.eshop.eshop.service.ProductService;
import com.eshop.eshop.service.ShoppingCartService;
import com.eshop.eshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1/shopping_carts")
@PreAuthorize("hasRole('ROLE_BUYER')")
public class ShoppingCartRestController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ShoppingCartRestController(ShoppingCartService shoppingCartService, ProductService productService, UserService userService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<Iterable<ShoppingCart>> getAll(){
        return new ResponseEntity<>(shoppingCartService.getAll(), HttpStatus.OK);
    }
    @PostMapping("add/")
    public ResponseEntity<ShoppingCartResponseDto> add(@RequestBody ShoppingCartDto shoppingCartDto){
        if(shoppingCartDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(shoppingCartDto.getUserId());
        if(user == null || user.getUsername() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!hasRole("ROLE_ADMIN") && !user.getUsername().contentEquals(getAuthorizedName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setUser(userService.findById(shoppingCartDto.getUserId()));
        shoppingCart.setCreated(new Date());
        shoppingCartService.add(shoppingCart);
        user.setShoppingCart(shoppingCartService.findById(shoppingCart.getId()));
        userService.updateCart(user.getId(), userService.findById(user.getId()));
        //log.info(userService.findById(Long.parseLong("1")).getShoppingCart().getId().toString());

        //shopping_carts/buy/1/
        return new ResponseEntity<>(ShoppingCartResponseDto.toShoppingCartResponseDto(user.getShoppingCart()), HttpStatus.CREATED);
    }
    @PatchMapping("addToShoppingCart/{userId}/{productId}/")
    public ResponseEntity addToShoppingCart(@PathVariable Long userId, @PathVariable Long productId){
        Product product = productService.findById(productId);
        if(product == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(userId);
        if(user == null || user.getUsername() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!hasRole("ROLE_ADMIN") && !user.getUsername().contentEquals(getAuthorizedName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        ShoppingCart shoppingCart = user.getShoppingCart();
        if(shoppingCart == null){
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setCreated(new Date());
            newShoppingCart.setUser(user);
            shoppingCartService.add(newShoppingCart);
            user.setShoppingCart(newShoppingCart);
            userService.updateCart(userId, user);
            shoppingCart = user.getShoppingCart();
        }
        shoppingCart.getProducts().add(product);
        userService.updateCart(userId, user);
        return new ResponseEntity(HttpStatus.OK);

    }
    @PatchMapping("removeFromShoppingCart/{userId}/{productId}/{count}/")
    public ResponseEntity removeProductFromShoppingCart(@PathVariable Long userId, @PathVariable Long productId, @PathVariable Integer count){
        Product product = productService.findById(productId);
        if(product == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(userId);
        if(user == null || user.getUsername() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!hasRole("ROLE_ADMIN") && !user.getUsername().contentEquals(getAuthorizedName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Product> products = user.getShoppingCart().getProducts().stream().filter((el)-> el.getId() == productId).limit(count).collect(Collectors.toList());
        user.getShoppingCart().getProducts().removeAll(products);
        userService.updateCart(userId, user);
        return new ResponseEntity(HttpStatus.OK);

    }
    @PatchMapping("removeAllProducts/{userId}/")
    public ResponseEntity removeAllProducts(@PathVariable Long userId){
        User user = userService.findById(userId);
        if(user == null || user.getUsername() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(!hasRole("ROLE_ADMIN") && !user.getUsername().contentEquals(getAuthorizedName())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        user.getShoppingCart().getProducts().clear();
        userService.updateCart(userId, user);
        return new ResponseEntity(HttpStatus.OK);

    }
    @PatchMapping("buy/{userId}")
    public ResponseEntity buy(@PathVariable Long userId){
        User user = userService.findById(userId);
        if(user == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        //order.setProducts(user.getShoppingCart().getProducts());
        //order.setProducts();
        //user.getOrders().add(order);

        order.setUser(user);
        order.setOrderStatus(OrderStatus.Processing);
        order.setCreated(new Date());
        user = userService.findById(userId);
        List<Product> products = new ArrayList<Product>();
        order.setProducts(products);
        for (Product product:
                user.getShoppingCart().getProducts()) {
            order.getProducts().add(productService.findById(product.getId()));
        }
        orderService.add(order);
        //log.info(user.getOrders().get(0).getProducts());
        orderService.update(order.getId(), order);
        user.getOrders().add(order);
        //user.getOrders().add(order);
        //user.getShoppingCart().getProducts().clear();
        user.getShoppingCart().getProducts().clear();
        userService.updateUser(userId, user);
        return new ResponseEntity<>(ShoppingCartResponseDto.toShoppingCartResponseDto(user.getShoppingCart()), HttpStatus.OK);
    }
    public String getAuthorizedName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if(!(authentication == null || authentication instanceof AnonymousAuthenticationToken)){
            return authentication.getName();
        }
        return null;
    }
    public static boolean hasRole(String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
