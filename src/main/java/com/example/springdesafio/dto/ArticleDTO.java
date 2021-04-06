package com.example.springdesafio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer productId;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer quantity;
    private Boolean freeShipping;
    private Integer prestige;

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", freeShipping=" + freeShipping +
                ", prestige='" + prestige + '\'' +
                '}';
    }
}