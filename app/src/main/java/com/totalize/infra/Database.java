package com.totalize.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Caminho para o banco de dados SQLite (use o caminho correto do seu arquivo)
    private static final String URL = "jdbc:sqlite:../database/test.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Estabelecendo a conexão
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexão com o SQLite estabelecida com sucesso.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
