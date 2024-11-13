package com.totalize.views.components;

import lombok.Getter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@Getter
public class PriceLabelComponent extends JPanel {

    // Getters para acessar os labels externamente, se necessário
    private final JLabel descLabel;
    private final JLabel valueLabel;

    public PriceLabelComponent(String description, String price) {
        // Configuração do JPanel com BoxLayout no eixo X
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Criação e configuração do descLabel (descrição)
        descLabel = new JLabel(description);
        descLabel.setForeground(Color.BLACK); // Estilize conforme suas cores
        descLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        descLabel.setMaximumSize(new Dimension(1000, 50));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Criação e configuração do priceLabel (preço)
        valueLabel = new JLabel(price);
        valueLabel.setForeground(Color.BLACK); // Estilize conforme suas cores
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        valueLabel.setMaximumSize(new Dimension(1000, 50));
        valueLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Adicionando os componentes ao JPanel (PriceLabelContainer)
        this.add(descLabel);
        this.add(Box.createHorizontalGlue()); // Empurra o preço para a direita
        this.add(valueLabel);
    }

}
