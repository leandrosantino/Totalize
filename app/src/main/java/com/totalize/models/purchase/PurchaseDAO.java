package com.totalize.models.purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.totalize.infra.Database;
import com.totalize.models.product.PurchasedProduct;

public class PurchaseDAO {

    public static List<Purchase> getALl() {
        List<Purchase> purchases = new ArrayList<>();

        String sql = "SELECT purchase_id, CPF, total_price, purchase_date FROM purchase";

        String productsSql = "SELECT PR.*, PP.amount FROM purchased_products as PP INNER JOIN product as PR ON PP.product_id = PR.product_id where purchase_id = (?)";

        try {
            Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet purchaseResultSet = stmt.executeQuery(sql);

            while (purchaseResultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(purchaseResultSet.getInt("purchase_id"));
                purchase.setCPF(purchaseResultSet.getString("CPF"));
                purchase.setTotalPrice(purchaseResultSet.getInt("total_price"));
                purchase.setDate(purchaseResultSet.getString("purchase_date"));

                List<PurchasedProduct> purchaseProducts = new ArrayList<>();

                PreparedStatement preparedStmt = conn.prepareStatement(productsSql);
                preparedStmt.setInt(1, purchase.getId());

                ResultSet productsResultSet = preparedStmt.executeQuery();

                while (productsResultSet.next()) {
                    PurchasedProduct product = new PurchasedProduct();
                    product.setId(productsResultSet.getInt("product_id"));
                    product.setCode(productsResultSet.getString("barcode"));
                    product.setDescription(productsResultSet.getString("description"));
                    product.setPrice(productsResultSet.getInt("price"));
                    product.setAmount(productsResultSet.getInt("amount"));

                    purchaseProducts.add(product);
                }

                purchase.setProducts(purchaseProducts);
                purchases.add(purchase);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return purchases;
    }

}