package com.totalize.views.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.totalize.views.utils.RoundBorder;

import lombok.Setter;

public class Button extends JButton {

    @Setter
    private int borderRadio = 10;
    @Setter
    private float borderStroke = 0;
    @Setter
    private Color borderColor;
    @Setter
    private int padding;

    public void setDimension(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
    }

    public Button() {
        super(null, null);
    }

    public Button(String texto) {
        super(texto);
        setContentAreaFilled(false);
        RoundBorder border = new RoundBorder(borderRadio, borderColor, borderStroke);
        setPadding(padding);
        setBorder(border);

        addMouseListener(new MouseAdapter() {

            private Color defaultBackground = getBackground();

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackground);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                defaultBackground = getBackground();
                setBackground(defaultBackground.darker());
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadio, borderRadio);
        super.paintComponent(g);
    }

}
