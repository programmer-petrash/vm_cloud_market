package com.eshop.eshop.dto;

import com.eshop.eshop.entity.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    public static ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart){
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setId(shoppingCart.getId());
        shoppingCartResponseDto.setUserId(shoppingCart.getUser().getId());
        return shoppingCartResponseDto;
    }
}
