package com.eshop.eshop.dto;

import com.eshop.eshop.entity.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    public static ShoppingCart toShoppingCart(ShoppingCartDto shoppingCartDto){
        if(shoppingCartDto == null){
            return null;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDto.getId());
        return shoppingCart;
    }
    public static ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart){
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCart.setId(shoppingCartDto.getId());
        return shoppingCartDto;
    }
}
