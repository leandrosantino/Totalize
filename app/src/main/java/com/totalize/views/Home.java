package com.totalize.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import com.totalize.views.components.Buttons.Button;
import com.totalize.views.components.Buttons.ButtonType;
import com.totalize.views.utils.RoundBorder;
import com.totalize.views.utils.ScannerListener;
import com.totalize.views.utils.StyleSystem;

public class Home extends JPanel {
    public Home() {

        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        rightPanel.setBackground(StyleSystem.Colors.LIGHT_GRAY);

        add(leftPanel);
        add(rightPanel);

        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Button button = new Button("", 40, 30, ButtonType.Secondary, MaterialDesignP.PLUS);
        Button button2 = new Button("", 40, 30, ButtonType.Emphasis, MaterialDesignT.TRASH_CAN);

        leftPanel.add(button);
        leftPanel.add(button2);

        JTextField text = new JTextField();
        leftPanel.add(text);
        text.setBorder(new RoundBorder(10, Color.BLACK, 1));
        text.setPreferredSize(new Dimension(100, 30));

        leftPanel.setFocusable(true);
        leftPanel.addKeyListener(new ScannerListener());
    }
}
