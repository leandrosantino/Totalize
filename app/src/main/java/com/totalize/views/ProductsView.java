package com.totalize.views;

import com.totalize.models.product.Product;
import com.totalize.models.product.ProductDAO;
import com.totalize.views.components.Buttons.Button;
import com.totalize.views.components.Buttons.ButtonType;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductsView extends JPanel {

    private final JTextField txtBarcode;
    private final JTextField txtDescription;
    private final JTextField txtPrice;
    private final JTable productTable;
    private final DefaultTableModel tableModel;
    private int selectedProductId = -1; // Armazena o ID do produto selecionado para edição

    public ProductsView() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Configurando os campos de entrada
        txtBarcode = new JTextField(10);
        txtDescription = new JTextField(20);
        txtPrice = new JTextField(10);

        JButton btnSave = new Button("Salvar", 80, 30, ButtonType.Success, MaterialDesignC.CONTENT_SAVE);
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveOrUpdateProduct()); // Altera o método do botão Salvar para salvar ou atualizar

        JButton btnEdit = new Button("Editar", 80, 30, ButtonType.Secondary, MaterialDesignP.PENCIL);
//        btnEdit.setBackground(Color.ORANGE);
        btnEdit.setFont(new Font("Arial", Font.BOLD, 12));
        btnEdit.setFocusPainted(false);
        btnEdit.addActionListener(e -> loadSelectedProduct());
        btnEdit.setEnabled(false); // Inicialmente desabilitado até que um produto seja selecionado

        Button btnDelete = new Button("Excluir", 80, 30, ButtonType.Primary, MaterialDesignD.DELETE_OUTLINE);
        btnDelete.setBackground(Color.RED);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteSelectedProduct());

        // Painel de título (topPanel)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("TOTALIZE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        topPanel.add(exitButton, BorderLayout.EAST);

        // Configurando a tabela de produtos
        tableModel = new DefaultTableModel(new String[]{"ID", "Código de Barras", "Descrição", "Preço"}, 0);
        productTable = new JTable(tableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setBackground(Color.WHITE);
        productTable.setShowGrid(false);
        productTable.setFont(new Font("Arial", Font.PLAIN, 12));
        productTable.setRowHeight(25);

        // Adiciona listener para ativar o botão Editar ao selecionar uma linha
        productTable.getSelectionModel().addListSelectionListener(e -> btnEdit.setEnabled(true));

        // Adicionando componentes ao painel de entrada
        JPanel inputPanel = new JPanel();
//        btnSave.setBackground(new Color(211, 211, 211));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        inputPanel.add(new JLabel("Código de Barras:"));
        inputPanel.add(txtBarcode);
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(txtDescription);
        inputPanel.add(new JLabel("Preço:"));
        inputPanel.add(txtPrice);
        inputPanel.add(btnSave);
        inputPanel.add(btnEdit); // Adiciona o botão de edição ao painel
        inputPanel.add(btnDelete);

        add(topPanel, BorderLayout.NORTH); // Adiciona o painel de título na parte superior
        add(inputPanel, BorderLayout.NORTH); // Adiciona o painel de entrada na parte superior
        add(new JScrollPane(productTable), BorderLayout.CENTER); // Adiciona a tabela centralizada

        // Carregando produtos ao iniciar
        loadProducts();
    }

    //  carregar produtos na tabela
    private void loadProducts() {
        List<Product> products = ProductDAO.getAll();
        tableModel.setRowCount(0); // Limpa a tabela

        for (Product product : products) {
            tableModel.addRow(new Object[]{
                product.getId(),
                product.getBarcode(),
                product.getDescription(),
                product.getPrice()
            });
        }
    }

    //  salvar ou atualizar um produto
    private void saveOrUpdateProduct() {
        String barcode = txtBarcode.getText();
        String description = txtDescription.getText();
        int price = Integer.parseInt(txtPrice.getText());

        if (selectedProductId == -1) { // Salva novo produto
            Product product = new Product(barcode, description, price);
            ProductDAO.createProduct(product);
        } else { // Atualiza produto existente
            Product product = new Product(selectedProductId, barcode, description, price);
            ProductDAO.updateProduct(product);
            selectedProductId = -1; // Reset o ID do produto selecionado
        }

        clearFields(); // Limpa os campos
        loadProducts(); // Recarrega a tabela
    }

    // carregar o produto selecionado nos campos para edição
    private void loadSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedProductId = (int) tableModel.getValueAt(selectedRow, 0); // Salva o ID do produto selecionado
            txtBarcode.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtDescription.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtPrice.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        }
    }

    //  excluir o produto selecionado
    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 0);
            ProductDAO.deleteProduct(productId);
            loadProducts(); // Recarrega a tabela
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
        }
    }

    //  limpar os campos de entrada
    private void clearFields() {
        txtBarcode.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        selectedProductId = -1; // Reset o ID do produto selecionado
    }
}
