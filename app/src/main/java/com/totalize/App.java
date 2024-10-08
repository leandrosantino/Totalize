package com.totalize;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.totalize.models.product.PurchasedProduct;
import com.totalize.models.purchase.Purchase;
import com.totalize.models.purchase.PurchaseDAO;

public class App {

    int i;

    public static void main(String[] args) throws SQLException {

        List<PurchasedProduct> products = new ArrayList<>();
        var product = new PurchasedProduct();
        product.setId(1);
        product.setAmount(5);
        products.add(product);

        Purchase purchase = new Purchase(
                null,
                "12345678909",
                1999,
                LocalDateTime.now(),
                products);

        PurchaseDAO.create(purchase);

    }

    void startWindow() {
        // Cria o frame (janela)
        JFrame frame = new JFrame("Tela Swing Básica");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria um painel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);

        // Adiciona um botão ao painel
        JButton button = new JButton("Clique aqui");
        panel.add(button);

        JTextPane text = new JTextPane();
        panel.add(text);

        AtomicInteger i = new AtomicInteger(0);
        button.addActionListener(e -> {
            text.setText("Leandro " + i);
            i.incrementAndGet();
        });

        panel.setFocusable(true);
        panel.addKeyListener(new KeyListener() {

            String barCode = "";

            @Override
            public void keyPressed(KeyEvent e) {
                String pressedKey = KeyEvent.getKeyText(e.getKeyCode());
                if (!pressedKey.equals("Enter")) {
                    this.barCode = this.barCode.concat(pressedKey);
                    return;
                }
                System.out.println(this.barCode);
                this.barCode = "";
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });

        // Torna a janela visível
        frame.setVisible(true);
    }

}
