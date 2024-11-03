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
        String sql = "SELECT product_id, barcode, description, price FROM product";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
                Statement stmt = conn.createStatement(); // Cria um objeto Statement para executar a query
                ResultSet rs = stmt.executeQuery(sql)) { // Executa a query e armazena o resultado em ResultSet

            // Percorre cada linha do resultado e cria um objeto Product para cada produto
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"), // Pega o ID do produto
                        rs.getString("barcode"), // Pega o código do produto
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
        String sql = "INSERT INTO product (barcode, description, price) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
                PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query para evitar SQL Injection

            // Define os parâmetros da query (substitui os "?" pelo valores reais)
            stmt.setString(1, product.getBarcode()); // Define o código do produto
            stmt.setString(2, product.getDescription()); // Define a descrição do produto
            stmt.setInt(3, product.getPrice()); // Define o preço do produto

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
        // Query SQL para verificar se já existe outro produto com o mesmo código de
        // barras
        String checkSql = "SELECT COUNT(*) FROM product WHERE barcode = ? AND product_id != ?";
        // Query SQL para atualizar as informações do produto com base no ID
        String updateSql = "UPDATE product SET barcode = ?, description = ?, price = ? WHERE product_id = ?";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
                PreparedStatement checkStmt = conn.prepareStatement(checkSql)) { // Prepara a query de verificação

            // Define os parâmetros para a query de verificação
            checkStmt.setString(1, product.getBarcode()); // Código do produto que está sendo atualizado
            checkStmt.setInt(2, product.getId()); // ID do produto a ser atualizado

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, product.getBarcode());
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

    public static Product getBybarcode(String productId) {
        // Define o produto que será retornado (pode ser null caso não seja encontrado)
        Product product = null;

        // Definindo a query SQL com um parâmetro para o ID
        String sql = "SELECT product_id, barcode, description, price FROM product WHERE barcode = ?";

        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
                PreparedStatement pstmt = conn.prepareStatement(sql)) { // Usa PreparedStatement para evitar injeção de
                                                                        // SQL

            // Define o valor do parâmetro (id) na query
            pstmt.setString(1, productId);

            // Executa a query e armazena o resultado em ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {

                // Verifica se houve algum resultado
                if (rs.next()) {
                    // Cria o objeto Product com os dados do resultado
                    product = new Product(
                            rs.getInt("product_id"), // Pega o ID do produto
                            rs.getString("barcode"), // Pega o código de barras
                            rs.getString("description"), // Pega a descrição do produto
                            rs.getInt("price")); // Pega o preço do produto
                }
            }
        } catch (SQLException e) {
            // Caso ocorra um erro durante a execução da query, ele será impresso aqui
            System.out.println(e.getMessage());
        }

        // Retorna o produto encontrado ou null se não houver correspondência
        return product;
    }

    // Função para excluir um produto do banco de dados
    public static void deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE product_id = ?";
    
        try (Connection conn = Database.connect(); // Estabelece a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query
    
            stmt.setInt(1, productId); // Define o ID do produto a ser excluído
            int linhasAfetadas = stmt.executeUpdate(); // Executa a operação de exclusão
    
            // Verifica se alguma linha foi afetada
            if (linhasAfetadas > 0) {
                System.out.println("Produto excluído com sucesso.");
            } else {
                System.out.println("Nenhum produto encontrado com o ID fornecido.");
            }
    
        } catch (SQLException e) {
            // Mensagem de erro, se a operação falhar
            System.err.println("Erro ao excluir o produto: " + e.getMessage());
        }
    }
    
}