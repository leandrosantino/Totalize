package com.totalize.views.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.totalize.views.utils.Style;

public class Header extends JPanel {

    public Header(int containerWidth) {
        setBackground(Style.Colors.BLACK);
        setPreferredSize(new Dimension(containerWidth, 40));
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("TOTALIZE", SwingConstants.CENTER);
        headerLabel.setForeground(Style.Colors.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        add(headerLabel, BorderLayout.CENTER);
    }

}
