package com.eshop.eshop.dto;

import com.eshop.eshop.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.P;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Float price;

    public static Product toProduct(ProductDto productDto){
        if(productDto == null){
            return null;
        }
        Product result = new Product();
        result.setId(productDto.getId());
        result.setName(productDto.getName());
        result.setPrice(productDto.getPrice());
        result.setDescription(productDto.getDescription());
        return result;
    }
    public static ProductDto toProductDto(Product product){
        if(product == null){
            return null;
        }
        ProductDto result = new ProductDto();
        result.setId(product.getId());
        result.setName(product.getName());
        result.setDescription(product.getDescription());
        result.setPrice(product.getPrice());
        return result;
    }
}
