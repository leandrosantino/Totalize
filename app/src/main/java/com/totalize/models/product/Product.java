package com.totalize.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    public Product() {
    }

    protected Integer id;
    protected String code;
    protected String description;
    protected Integer price;

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
