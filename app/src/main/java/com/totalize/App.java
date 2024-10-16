package com.totalize;

import java.awt.BorderLayout;
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
        JFrame frame = new JFrame("Tela Swing");
        int size = 1210;
        float p = 0.66f;
        frame.setSize(size, Math.round(size * p));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Coniainer Principal
        JPanel main = new JPanel(new BorderLayout());
        // Buy buy = new Buy();

        // Components
        Header header = new Header(frame.getWidth());
        Home home = new Home();

        main.add(header, BorderLayout.NORTH);
        main.add(home, BorderLayout.CENTER);

        // frame.addComponentListener(new ComponentAdapter() {
        // @Override
        // public void componentResized(ComponentEvent e) {
        // buy.teste();
        // }
        // });

        frame.add(main);
        frame.setVisible(true);
    }

}
