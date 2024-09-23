package com.totalize.models.purchase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.totalize.models.product.PurchasedProduct;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Purchase {

    public Purchase() {
    }

    final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private Integer id;
    private String CPF;
    private Integer totalPrice;
    private LocalDateTime date;
    private List<PurchasedProduct> products;

    String getDateAsString() {
        return this.date.format(formato);
    }

    void setDate(String date) {
        this.date = LocalDateTime.parse(date, formato);
    }

    @Override
    public String toString() {
        return "Purchase {id=" + id + ", CPF=" + CPF + ", totalPrice=" + totalPrice + ", date=" + date + ", products="
                + products + "}";
    }

}
