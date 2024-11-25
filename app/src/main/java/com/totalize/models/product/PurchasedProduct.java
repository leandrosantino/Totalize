package com.totalize.models.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchasedProduct extends Product {

    private Integer amount;

    public PurchasedProduct(Product product, Integer amount) {
        super(product.getId(), product.getBarcode(), product.getDescription(), product.getPrice());
        this.amount = amount;
    }

    public PurchasedProduct() {
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + this.id +
                ", barcode='" + this.barcode + '\'' +
                ", description=" + this.description +
                ", price=" + this.price +
                ", amount=" + this.amount +
                '}';
    }
}
