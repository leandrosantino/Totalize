package com.totalize;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.totalize.views.Home;
import com.totalize.views.ProductsView;
import com.totalize.views.components.Header;

public class App {

    private static final JPanel home = new Home();
    private static final ProductsView products = new ProductsView();


    public static void main(String[] args) {
        startWindow();
    }

    public static void startWindow() {
        JFrame frame = new JFrame("Totalize");
        int size = 1350;
        float p = 0.60f;
        frame.setSize(size, Math.round(size * p));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());

        Header header = new Header(frame.getWidth(), () -> {

            if (main.isAncestorOf(home)) {
                main.remove(home);
                main.add(products);
            } else {
                main.remove(products);
                main.add(home);
            }
            main.revalidate();
            main.repaint();
        });

        main.add(header, BorderLayout.NORTH);
        main.add(home, BorderLayout.CENTER);

        frame.add(main);
        frame.setVisible(true);
    }

}
