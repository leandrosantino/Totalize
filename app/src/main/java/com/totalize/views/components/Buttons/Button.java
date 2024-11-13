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
import com.totalize.views.utils.Style;
import com.totalize.views.utils.Style.Colors;

import lombok.Setter;

@Setter
public class Button extends JButton {

    private int borderRadio = 10;
    private float borderStroke = 0;
    private Color borderColor;
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
        setFont(Style.BTN_FONT);

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
                bgColor = Colors.SECONDARY;
                break;
            case Emphasis:
                bgColor = Colors.ERROR;
                break;
            case Success:
                bgColor = new Color(34,139,34);
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
        Dimension dimension = new Dimension(width, height);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
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
