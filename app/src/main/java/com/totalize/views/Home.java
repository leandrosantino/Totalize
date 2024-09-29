package com.totalize.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import com.totalize.views.components.Button;
import com.totalize.views.utils.RoundBorder;
import com.totalize.views.utils.StyleSystem;

public class Home extends JPanel {
    public Home() {

        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Adiciona um botão ao painel
        Button button = new Button("Plus 5");
        button.setDimension(100, 30);
        button.setForeground(StyleSystem.Colors.WHITE);
        button.setBackground(StyleSystem.Colors.PRIMARY);
        button.setIcon(FontIcon.of(MaterialDesignP.PLUS, 20, StyleSystem.Colors.WHITE));
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        // button.setVerticalTextPosition(SwingConstants.CENTER);

        Button button2 = new Button("Plus");
        button2.setDimension(100, 30);
        button2.setForeground(StyleSystem.Colors.WHITE);
        button2.setBackground(StyleSystem.Colors.ERROR);

        this.add(button);
        this.add(button2);

        JTextField text = new JTextField();
        this.add(text);
        text.setBorder(new RoundBorder(10, Color.BLACK, 1));
        text.setPreferredSize(new Dimension(100, 30));

        AtomicInteger i = new AtomicInteger(0);
        button.addActionListener(e -> {
            text.setText("Leandro" + i);
            i.incrementAndGet();
        });

        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {

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
    }
}
