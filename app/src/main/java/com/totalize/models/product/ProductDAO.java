package com.totalize.models.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.totalize.infra.Database;

public class ProductDAO {

    // Função para obter todos os produtos do banco de dados
    public static List<Product> getAll() {
        // Cria uma lista para armazenar os produtos que serão recuperados
        List<Product> products = new ArrayList<>();

        // Definindo a query SQL que seleciona todos os produtos
        String sql = "SELECT product_id, code, description, price FROM product";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
                Statement stmt = conn.createStatement(); // Cria um objeto Statement para executar a query
                ResultSet rs = stmt.executeQuery(sql)) { // Executa a query e armazena o resultado em ResultSet

            // Percorre cada linha do resultado e cria um objeto Product para cada produto
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"), // Pega o ID do produto
                        rs.getString("code"), // Pega o código do produto
                        rs.getString("description"), // Pega a descrição do produto
                        rs.getInt("price")); // Pega o preço do produto

                // Adiciona o produto à lista de produtos
                products.add(product);
            }
        } catch (SQLException e) {
            // Caso ocorra um erro durante a execução da query, ele será impresso aqui
            System.out.println(e.getMessage());
        }

        // Retorna a lista de produtos recuperados
        return products;
    }

    // Função para criar um novo produto no banco de dados
    public static void createProduct(Product product) {
        // Query SQL para inserir um novo produto no banco de dados
        String sql = "INSERT INTO product (code, description, price) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query para evitar SQL Injection

            // Define os parâmetros da query (substitui os "?" pelo valores reais)
            stmt.setString(1, product.getCode());        // Define o código do produto
            stmt.setString(2, product.getDescription()); // Define a descrição do produto
            stmt.setInt(3, product.getPrice());          // Define o preço do produto

            // Executa a query para inserir o novo produto
            stmt.executeUpdate();
            System.out.println("Produto criado com sucesso!");

        } catch (SQLException e) {
            // Caso ocorra um erro durante a execução da query, ele será impresso aqui
            System.out.println("Erro ao criar produto: " + e.getMessage());
        }
    }

    // Função para atualizar um produto no banco de dados
    public static void updateProduct(Product product) {
        // Query SQL para verificar se já existe outro produto com o mesmo código de barras
        String checkSql = "SELECT COUNT(*) FROM product WHERE code = ? AND product_id != ?";
        // Query SQL para atualizar as informações do produto com base no ID
        String updateSql = "UPDATE product SET code = ?, description = ?, price = ? WHERE product_id = ?";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) { // Prepara a query de verificação

            // Define os parâmetros para a query de verificação
            checkStmt.setString(1, product.getCode()); // Código do produto que está sendo atualizado
            checkStmt.setInt(2, product.getId());      // ID do produto a ser atualizado
            
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, product.getCode());
                        updateStmt.setString(2, product.getDescription());
                        updateStmt.setInt(3, product.getPrice());
                        updateStmt.setInt(4, product.getId());

                        updateStmt.executeUpdate();
                        System.out.println("Produto atualizado com sucesso!");
                    }
                } else {
                    System.out.println("Erro: Já existe um produto com o mesmo código.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
}
