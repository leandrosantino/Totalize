package com.totalize.views.components.Buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import com.totalize.views.utils.RoundBorder;
import com.totalize.views.utils.StyleSystem;
import com.totalize.views.utils.StyleSystem.Colors;

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

    public Button() {
        super(null, null);
    }

    public Button(String texto) {
        super(texto);
        setContentAreaFilled(false);
        RoundBorder border = new RoundBorder(borderRadio, borderColor, borderStroke);
        setPadding(padding);
        setBorder(border);
        setFont(StyleSystem.BTN_FONT);

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

    public Button(String text, int width, int height, ButtonType type, Ikon icon) {
        this(text);
        setDimension(width, height);
        Color bgColor = Colors.BLACK, foreColor = Colors.WHITE, iconColor = Colors.WHITE;
        switch (type) {
            case Primary:
                bgColor = Colors.PRIMARY;
                break;
            case Secondary:
                bgColor = Colors.SECODARY;
                break;
            case Emphasis:
                bgColor = Colors.ERROR;
                break;
            default:
                break;
        }
        setBackground(bgColor);
        setForeground(foreColor);
        if (icon != null) {
            setIcon(FontIcon.of(icon, 20, iconColor));
        }
        setHorizontalTextPosition(SwingConstants.LEFT);
    }

    public Button(String text, int width, int height, ButtonType type) {
        this(text, width, height, type, null);
    }

    public void setDimension(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
    }

    public void setFontSize(float size) {
        setFont(getFont().deriveFont(size));
    }

    public void setFontWeight(int style) {
        setFont(getFont().deriveFont(style));
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
