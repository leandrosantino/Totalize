package com.totalize.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private Integer id;
    private String code;
    private String description;
    private Integer price;

}
