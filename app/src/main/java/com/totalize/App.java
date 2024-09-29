package com.totalize;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.totalize.views.Home;
import com.totalize.views.components.Header;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final float FACTOR = 0.85f;

        JFrame frame = new JFrame("Tela Swing");
        frame.setSize(Math.round(screenSize.width * FACTOR), Math.round(screenSize.height * FACTOR));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Coniainer Principal
        JPanel main = new JPanel(new BorderLayout());

        // Components
        Header header = new Header(frame.getWidth());
        Home home = new Home();

        main.add(header, BorderLayout.NORTH);
        main.add(home, BorderLayout.CENTER);

        frame.add(main);
        frame.setVisible(true);
    }

}
