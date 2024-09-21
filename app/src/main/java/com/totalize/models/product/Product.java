package com.totalize.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    public Product() {
    }

    private Integer id;
    private String code;
    private String description;
    private Integer price;

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description=" + description +
                ", price=" + price +
                '}';
    }

}
