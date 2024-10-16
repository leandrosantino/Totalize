package com.totalize.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.totalize.views.components.ProductTable;
import com.totalize.views.utils.Style;

public class Buy extends JPanel {
    private static final long serialVersionUID = 1L;
    private DefaultListModel<String> modeloLista; // Modelo para a lista de compras
    private JLabel labelTotal;
    private double total;

    private JTable tabelaProdutos;

    public Buy() {

        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());

        // Modelo da lista de produtos
        modeloLista = new DefaultListModel<>();
        JList<String> listaCompras = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaCompras);
        listaCompras.setBackground(Color.WHITE);
        listaCompras.setForeground(Color.BLACK);
        listaCompras.setFont(new Font("Arial", Font.PLAIN, 14));

        // Label do total
        labelTotal = new JLabel("Total: 0,00 R$");
        labelTotal.setHorizontalAlignment(JLabel.CENTER); // Centraliza o label do total
        labelTotal.setForeground(Color.BLACK);
        labelTotal.setFont(new Font("Arial", Font.BOLD, 16));

        // Botão de finalizar compra
        JButton btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setPreferredSize(new Dimension(120, 30));
        btnFinalizar.setBackground(Color.BLACK);
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnFinalizar.setFocusPainted(false); // Remove o foco do botão

        ProductTable model = new ProductTable();

        tabelaProdutos = new JTable(model);

        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizador3 = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                // Obtém o componente padrão
                Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                // Define a borda (interna ou externa)
                Border borda = BorderFactory.createLineBorder(Style.Colors.GRAY, 1); // Borda preta de 2px
                ((JLabel) componente).setBorder(borda);

                // Centralizar texto no cabeçalho (opcional)
                setHorizontalAlignment(SwingConstants.CENTER);

                return componente;
            }
        };
        DefaultTableCellRenderer centralizador2 = new DefaultTableCellRenderer();

        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        centralizador3.setHorizontalAlignment(SwingConstants.CENTER);
        centralizador2.setHorizontalAlignment(SwingConstants.LEFT);

        centralizador3.setBackground(Style.Colors.LIGHT_GRAY);
        centralizador2.setBackground(Style.Colors.LIGHT_GRAY);

        tabelaProdutos.getTableHeader()
                .getColumnModel()
                .getColumn(0)
                .setHeaderRenderer(centralizador3);
        tabelaProdutos.getTableHeader()
                .getColumnModel()
                .getColumn(1)
                .setHeaderRenderer(centralizador3);

        // Aplica o renderizador para todas as colunas

        tabelaProdutos.getColumnModel().getColumn(1).setCellRenderer(centralizador);

        JScrollPane scrollPaneTabela = new JScrollPane(tabelaProdutos);

        tabelaProdutos.setBackground(Color.WHITE);
        tabelaProdutos.setForeground(Color.BLACK);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaProdutos.setFillsViewportHeight(true);

        // Adiciona os componentes ao contentPane
        add(scrollPaneTabela, BorderLayout.WEST); // Tabela de produtos à esquerda
        add(scrollPane, BorderLayout.EAST); // Lista de compras à direita
        add(labelTotal, BorderLayout.NORTH); // Label do total no topo
        add(btnFinalizar, BorderLayout.SOUTH); // Botão de finalizar compra na parte inferior

        // Ação do botão finalizar compra
        btnFinalizar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Compra finalizada! Total: " + String.format("%.2f", total) + " R$");
            total = 0; // Reseta o total
            modeloLista.clear(); // Limpa a lista de compras
            labelTotal.setText("Total: 0,00 R$"); // Reseta o texto do total
        });

    }

    public void teste() {
        int w1 = Math.round(tabelaProdutos.getWidth() * 0.8f);
        int w2 = Math.round(tabelaProdutos.getWidth() * 0.2f);

        tabelaProdutos.getColumnModel().getColumn(0).setMinWidth(w1);
        tabelaProdutos.getColumnModel().getColumn(0).setMaxWidth(w1);
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(w1);

        tabelaProdutos.getColumnModel().getColumn(1).setMinWidth(w2);
        tabelaProdutos.getColumnModel().getColumn(1).setMaxWidth(w2);
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(w2);
    }
}
