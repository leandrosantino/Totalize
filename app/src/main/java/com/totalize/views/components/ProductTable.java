package com.totalize.views.components;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.totalize.models.product.PurchasedProduct;

public class ProductTable extends AbstractTableModel {

    private final List<PurchasedProduct> products;
    private final String[] colunas = { "Código", "Descrição", "Qtd.", "Valor" };

    public ProductTable() {
        products = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return products.size(); // Quantidade de linhas
    }

    @Override
    public int getColumnCount() {
        return colunas.length; // Quantidade de colunas
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column]; // Nome das colunas
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Não permite edição das células
    }

    public void addItem(PurchasedProduct product) {
        products.add(product);
        fireTableRowsInserted(0, products.size() - 1);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < products.size()) {
            products.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }

    public void clear() {
        products.clear();
        fireTableDataChanged();
    }

    public List<PurchasedProduct> getList() {
        return products;
    }

    public PurchasedProduct getItem(int index) {
        return products.get(index);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PurchasedProduct product = products.get(rowIndex);

        Locale brasil = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brasil);

        int totalPrice = product.getPrice() * product.getAmount();

        Float price = Float.parseFloat(Integer.toString(totalPrice)) / 100f;

        return switch (columnIndex) {
            case 0 -> product.getBarcode();
            case 1 -> product.getDescription();
            case 2 -> product.getAmount();
            case 3 -> numberFormat.format(price);
            default -> null;
        };
    }

}
