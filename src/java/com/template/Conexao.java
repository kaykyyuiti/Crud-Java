package com.template;


public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/Lutadores";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "ctibauru";

    public static Connection obterConexao() {
        try {
            // Retorna uma nova instancia de conexao com o banco
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Falha critica na conexao com o banco: " + e.getMessage());
        }
    }
}
