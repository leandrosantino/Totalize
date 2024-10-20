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
        startWindow();
    }

    public static void startWindow() {
        JFrame frame = new JFrame("Totalize");
        int size = 1350;
        float p = 0.60f;
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
