package com.totalize.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Supplier;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;

import com.totalize.models.product.Product;
import com.totalize.models.product.ProductDAO;
import com.totalize.models.product.PurchasedProduct;
import com.totalize.views.components.PriceLabelComponent;
import com.totalize.views.components.ProductTable;
import com.totalize.views.components.Buttons.Button;
import com.totalize.views.components.Buttons.ButtonType;
import com.totalize.views.utils.GlobalScannerListener;
import com.totalize.views.utils.Style;

public class Home extends JPanel {

    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private final JPanel leftPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();

    private final ProductTable tableModel = new ProductTable();

    private final JTable productTable = new JTable(tableModel);

    private final JLabel totalLabel = new JLabel("Total: R$ 0,00");
    private final JLabel descriptionLabel = new JLabel("--------------");

    private final JPanel totalLabelContainer = new JPanel();
    private final JPanel descriptionLabelContainer = new JPanel();
    private Boolean inExecution = false;

    private final PriceLabelComponent priceLabelComponent = new PriceLabelComponent("Valor unitário:", "R$ 0,00");
    private final PriceLabelComponent amountLabelComponent = new PriceLabelComponent("Quantidade:", "0");
    private final PriceLabelComponent subtotalLabelComponent = new PriceLabelComponent("Subtotal:", "R$ 0,00");
    private final PriceLabelComponent barcodeLabelComponent = new PriceLabelComponent("Código de barras:", "--------------");

    public Home() {
        setLayout(new GridLayout(1, 2));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Style.Colors.LIGHT_GRAY);

        Border border = new EmptyBorder(10, 10, 10, 10);
        leftPanel.setBorder(border);
        rightPanel.setBorder(border);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        add(leftPanel);
        add(rightPanel);

        createLeftPanel();
        createRightPanel();

        Supplier<Boolean> canExecute = () -> !inExecution;

        setFocusable(true);
        new GlobalScannerListener(barcode -> {
            inExecution = true;
            System.out.println(barcode);

            Product product = ProductDAO.getBybarcode(barcode);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Este produto não está cadastrado!");
                inExecution = false;
                return;
            }

            Integer amount = 0;
            while (amount <= 0) try {
                String input = JOptionPane.showInputDialog(this, "Digite a quantidade de item:", "1");
                if (input == null) {
                    inExecution = false;
                    return;
                }
                amount = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {
            }

            PurchasedProduct purchasedProduct = new PurchasedProduct(product, amount);
            tableModel.addItem(purchasedProduct);

            descriptionLabel.setText(product.getDescription());
            totalLabel.setText("Total: " + calculateTotal());
            priceLabelComponent.getValueLabel().setText(formatToCurrency(product.getPrice()));
            amountLabelComponent.getValueLabel().setText(amount.toString());
            subtotalLabelComponent.getValueLabel().setText(formatToCurrency(product.getPrice() * amount));
            barcodeLabelComponent.getValueLabel().setText(barcode);
            inExecution = false;
        }, canExecute);
    }

    private String calculateTotal() {
        int accumulate = 0;
        for (PurchasedProduct product : tableModel.getList()) {
            accumulate += product.getPrice() * product.getAmount();
        }
        return formatToCurrency(accumulate);
    }

    private String formatToCurrency(Integer value) {
        Float total = Float.parseFloat(value.toString()) / 100f;
        return numberFormat.format(total);
    }

    private void createRightPanel() {

        totalLabel.setForeground(Style.Colors.BLACK);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 48));
        totalLabel.setMaximumSize(new Dimension(1000, 50));
        totalLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        descriptionLabel.setForeground(Style.Colors.BLACK);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 38));
        descriptionLabel.setMaximumSize(new Dimension(1000, 50));
        descriptionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        totalLabelContainer.setLayout(new BoxLayout(totalLabelContainer, BoxLayout.X_AXIS));
        totalLabelContainer.add(Box.createHorizontalGlue()); // Isso empurra o label para a direita
        totalLabelContainer.add(totalLabel);

        descriptionLabelContainer.setLayout(new BoxLayout(descriptionLabelContainer, BoxLayout.X_AXIS));
        descriptionLabelContainer.add(Box.createHorizontalGlue()); // Isso empurra o label para a direita
        descriptionLabelContainer.add(descriptionLabel);

        Button finalizeButton = new Button("Finalizar", 500, 40, ButtonType.Primary, MaterialDesignC.CART_VARIANT);
        finalizeButton.setMaximumSize(new Dimension(1000, 50));
        finalizeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalizeButton.setFont(new Font("Arial", Font.PLAIN, 18));

        finalizeButton.addActionListener(e -> {

            if (tableModel.getList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum produto foi registrado!");
                return;
            }

            if (JOptionPane.showConfirmDialog(this, "Realmente deseja finalizar a Compra?") > 0) {
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Compra finalizada com sucesso! \n Aguarde a Emissão da nota!");
            descriptionLabel.setText("--------------");
            totalLabel.setText("Total: R$ 0,00");
            priceLabelComponent.getValueLabel().setText("R$ 0,00");
            amountLabelComponent.getValueLabel().setText("0");
            subtotalLabelComponent.getValueLabel().setText("R$ 0,00");
            barcodeLabelComponent.getValueLabel().setText("--------------");
            tableModel.clear();
        });

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(descriptionLabelContainer);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(barcodeLabelComponent);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        rightPanel.add(priceLabelComponent);
        rightPanel.add(amountLabelComponent);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(subtotalLabelComponent);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(totalLabelContainer);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(finalizeButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void createLeftPanel() {

        JLabel title = new JLabel("Produtos", SwingConstants.LEFT);
        title.setForeground(Style.Colors.BLACK);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        productTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        productTable.getTableHeader().setResizingAllowed(false);
        productTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPaneTabela = new JScrollPane(productTable);

        scrollPaneTabela.setPreferredSize(new Dimension(500, 500));

        productTable.setBackground(Color.WHITE);
        productTable.setForeground(Color.BLACK);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setFillsViewportHeight(true);

        Button removeButton = new Button("Remover", 90, 25, ButtonType.Emphasis, MaterialDesignD.DELETE);
        removeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        removeButton.setMaximumSize(new Dimension(90, 50));
        removeButton.setFont(new Font("Arial", Font.BOLD, 11));

        removeButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item antes de remover!");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Realmente deseja remover este item?") > 0) {
                return;
            }
            tableModel.removeItem(selectedRow);
            JOptionPane.showMessageDialog(this, "Item removido!");
        });

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.add(title);
        buttonContainer.add(Box.createHorizontalGlue());
        buttonContainer.add(removeButton);
        buttonContainer.setBackground(Style.Colors.LIGHT_GRAY);

        // leftPanel.add(titleContainer);
        leftPanel.add(buttonContainer);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(scrollPaneTabela);
    }
}
