package com.totalize.models.product;

import lombok.Getter;
import lombok.Setter;

public class PurchasedProduct extends Product {
    @Setter
    @Getter
    private Integer amount;

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
