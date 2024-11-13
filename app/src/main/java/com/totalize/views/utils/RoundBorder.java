package com.totalize.views.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.Border;

import lombok.Setter;

public class RoundBorder implements Border {
    private final int radio;
    private final Color borderColor;
    private final float stroke;

    @Setter
    private int padding = 6;

    public RoundBorder(int raio, Color borderColor, float stroke) {
        this.radio = raio;
        this.borderColor = borderColor;
        this.stroke = stroke;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(padding, padding, padding, padding);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.draw(new RoundRectangle2D.Double(x + stroke / 2, y + stroke / 2,
                width - stroke, height - stroke,
                radio, radio));
    }
}
