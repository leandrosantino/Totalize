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

    private static final Connection conn = Database.connect();

    public static List<Purchase> getAll() {
        List<Purchase> purchases = new ArrayList<>();

        String sql = "SELECT purchase_id, CPF, total_price, purchase_date FROM purchase";

        String productsSql = "SELECT PR.*, PP.amount FROM purchased_products as PP INNER JOIN product as PR ON PP.product_id = PR.product_id where purchase_id = (?)";

        try {

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
                    product.setBarcode(productsResultSet.getString("barcode"));
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

    public static void create(Purchase purchase) throws SQLException {

        String createPurchaseSql = "INSERT INTO purchase (CPF, total_price, purchase_date) VALUES (?, ?, ?)";
        String connectProductsSql = "INSERT INTO purchased_products (product_id, purchase_id, amount) VALUES (?, ?, ?)";

        try {
            PreparedStatement purchaseStmt = conn.prepareStatement(createPurchaseSql);

            purchaseStmt.setString(1, purchase.getCPF());
            purchaseStmt.setInt(2, purchase.getTotalPrice());
            purchaseStmt.setString(3, purchase.getDateAsString());
            purchaseStmt.executeUpdate();

            Integer purchaseId = getLastElementId();
            System.out.println(purchaseId);

            for (PurchasedProduct product : purchase.getProducts()) {
                PreparedStatement productStmt = conn.prepareStatement(connectProductsSql);
                productStmt.setInt(1, product.getId());
                productStmt.setInt(2, purchaseId);
                productStmt.setInt(3, product.getAmount());
                productStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static Integer getLastElementId() {
        String sql = "SELECT purchase_id FROM purchase ORDER BY purchase_id DESC LIMIT 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("purchase_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

}