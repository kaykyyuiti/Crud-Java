package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/Lutadores";

    private static final String USUARIO = "postgres";
    private static final String SENHA = "ctibauru";

    public static Connection obterConexao() {

        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Falha crítica na conexão com o banco: "
                            + e.getMessage()
            );
        }
    }
}