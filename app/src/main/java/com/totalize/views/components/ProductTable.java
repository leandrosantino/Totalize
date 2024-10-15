package com.totalize.views.components;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.totalize.models.product.Product;

public class ProductTable extends AbstractTableModel {

    private List<Product> products;
    private String[] colunas = { "Descrição", "Preço" };

    public ProductTable(List<Product> products) {
        this.products = products;
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);

        Locale brasil = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brasil);
        Float price = Float.parseFloat(product.getPrice().toString()) / 100f;

        switch (columnIndex) {
            case 0:
                return product.getDescription();
            case 1:
                return numberFormat.format(price);
            default:
                return null;
        }
    }

}
