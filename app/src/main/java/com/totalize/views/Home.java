package com.totalize.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Home extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane; 
    private DefaultListModel<String> modeloLista; // Modelo para a lista de compras
    private JLabel labelTotal; 
    private double total; 

    public Home() {
        // Configurações da janela
        setTitle("Totalize");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza a janela
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE); 
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

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
        
        // Tabela de produtos
        String[] colunas = {"Produto", "Preço unit."};
        Object[][] dados = {
            {"1kg Macarrão", "8,00 R$"},
            {"1L Óleo", "5,00 R$"},
            {"2kg Arroz", "12,00 R$"},
            {"1kg Feijão", "7,00 R$"}
        };
        
        
        DefaultTableModel model = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Não permite edição das células
            }
        };
        
        JTable tabelaProdutos = new JTable(model);
        JScrollPane scrollPaneTabela = new JScrollPane(tabelaProdutos);
        
        tabelaProdutos.setBackground(Color.WHITE);
        tabelaProdutos.setForeground(Color.BLACK);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14)); 
        tabelaProdutos.setFillsViewportHeight(true);
        
        
        // Adiciona os componentes ao contentPane
        contentPane.add(scrollPaneTabela, BorderLayout.WEST); // Tabela de produtos à esquerda
        contentPane.add(scrollPane, BorderLayout.EAST); // Lista de compras à direita
        contentPane.add(labelTotal, BorderLayout.NORTH); // Label do total no topo
        contentPane.add(btnFinalizar, BorderLayout.SOUTH); // Botão de finalizar compra na parte inferior
        
        // Ação do botão finalizar compra
        btnFinalizar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Compra finalizada! Total: " + String.format("%.2f", total) + " R$");
            total = 0; // Reseta o total
            modeloLista.clear(); // Limpa a lista de compras
            labelTotal.setText("Total: 0,00 R$"); // Reseta o texto do total
        });

        pack(); // Ajusta o tamanho da janela com base nos componentes
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home frame = new Home();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}