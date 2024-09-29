package com.totalize;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.totalize.views.Home;
import com.totalize.views.components.Header;
import com.totalize.views.utils.StyleSystem;

public class App {

    int i;

    public static void main(String[] args) throws SQLException {

        // List<PurchasedProduct> products = new ArrayList<>();
        // var product = new PurchasedProduct();
        // product.setId(1);
        // product.setAmount(5);
        // products.add(product);

        // Purchase purchase = new Purchase(
        // null,
        // "12345678909",
        // 1999,
        // LocalDateTime.now(),
        // products);

        // PurchaseDAO.create(purchase);

        startWindow();

    }

    public static void startWindow() {
        JFrame frame = new JFrame("Tela Swing");
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout()); // Coniainer Principal

        Header header = new Header(frame.getWidth());
        main.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1, 2));
        main.add(content, BorderLayout.CENTER);

        Home home = new Home();
        content.add(home);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(StyleSystem.Colors.LIGHT_GRAY);
        content.add(rightPanel);

        frame.add(main);
        frame.setVisible(true);
    }

}
