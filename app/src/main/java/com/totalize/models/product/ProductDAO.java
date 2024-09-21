package com.totalize.models.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.totalize.infra.Database;

public class ProductDAO {

    public static List<Product> getALl() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT product_id, barcode, description, price FROM product";

        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("barcode"),
                        rs.getString("description"),
                        rs.getInt("price"));

                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }
}
