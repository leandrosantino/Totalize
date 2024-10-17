package com.totalize.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;

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

import com.totalize.models.product.Product;
import com.totalize.models.product.ProductDAO;
import com.totalize.models.product.PurchasedProduct;
import com.totalize.views.components.PriceLabelComponent;
import com.totalize.views.components.ProductTable;
import com.totalize.views.components.Buttons.Button;
import com.totalize.views.components.Buttons.ButtonType;
import com.totalize.views.utils.ScannerListener;
import com.totalize.views.utils.Style;

public class Home extends JPanel {

    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    private ProductTable tableModel = new ProductTable();

    private JTable productTable = new JTable(tableModel);

    private Border border = new EmptyBorder(10, 10, 10, 10);

    private JLabel totalLabel = new JLabel("Total: R$ 0,00");
    private JLabel descriptionLabel = new JLabel("Descrição");

    private JPanel totalLabelContainer = new JPanel();
    private JPanel descriptionLabelContainer = new JPanel();

    private PriceLabelComponent priceLabelComponent = new PriceLabelComponent("Valor unitário:", "R$ 0,00");
    private PriceLabelComponent amountLabelComponent = new PriceLabelComponent("Quantidade:", "0");
    private PriceLabelComponent subtotalLabelComponent = new PriceLabelComponent("Subtotal:", "R$ 0,00");
    private PriceLabelComponent barcodeLabelComponent = new PriceLabelComponent("Código de barras:", "0000000000");

    public Home() {
        setLayout(new GridLayout(1, 2));

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Style.Colors.LIGHT_GRAY);

        leftPanel.setBorder(border);
        rightPanel.setBorder(border);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        add(leftPanel);
        add(rightPanel);

        createLeftPanel();
        createRightPanel();

        setFocusable(true);
        addKeyListener(new ScannerListener(barcode -> {
            System.out.println(barcode);

            Product product = ProductDAO.getBybarcode(barcode);
            if (product == null) {
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Digite a quantidade de item:", "1");

            Integer amount = 0;
            while (amount <= 0) {
                try {
                    amount = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            PurchasedProduct purchasedProduct = new PurchasedProduct(product, amount);
            tableModel.addItem(purchasedProduct);

            descriptionLabel.setText(product.getDescription());
            totalLabel.setText("Total: " + calculateTotal());
            priceLabelComponent.getValueLabel().setText(formatToCurrence(product.getPrice()));
            amountLabelComponent.getValueLabel().setText(amount.toString());
            subtotalLabelComponent.getValueLabel().setText(formatToCurrence(product.getPrice() * amount));
            barcodeLabelComponent.getValueLabel().setText(barcode);
        }));
    }

    private String calculateTotal() {
        Integer accumulate = 0;
        for (PurchasedProduct product : tableModel.getList()) {
            accumulate += product.getPrice() * product.getAmount();
        }
        return formatToCurrence(accumulate);
    }

    private String formatToCurrence(Integer value) {
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
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.LINE_AXIS));
        titleContainer.setBorder(border);
        titleContainer.add(title);
        titleContainer.setBackground(Style.Colors.LIGHT_GRAY);
        titleContainer.setPreferredSize(new Dimension(500, 50));

        productTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        productTable.getTableHeader().setResizingAllowed(false);
        productTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPaneTabela = new JScrollPane(productTable);

        scrollPaneTabela.setPreferredSize(new Dimension(500, 500));

        productTable.setBackground(Color.WHITE);
        productTable.setForeground(Color.BLACK);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setFillsViewportHeight(true);

        leftPanel.add(titleContainer);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(scrollPaneTabela);
    }
}
