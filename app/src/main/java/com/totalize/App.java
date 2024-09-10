package com.totalize;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class App {

    int i;

    public static void main(String[] args) {
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
