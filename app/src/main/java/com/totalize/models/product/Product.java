package com.totalize.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    public Product() {
    }

    protected Integer id;
    protected String barcode;
    protected String description;
    protected Integer price;

    public Product(String barcode, String description, Integer price) {
        this.barcode = barcode;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", description=" + description +
                ", price=" + price +
                '}';
    }

}
