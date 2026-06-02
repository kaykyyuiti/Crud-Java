package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CampeaoBrasileiroDAO {

    private static final Logger logger = Logger.getLogger(CampeaoBrasileiroDAO.class.getName());

    public void inserir(CampeaoBrasileiroDTO novo) {
        String sql = "INSERT INTO lutadores (nome,categoria,genero,idade_campeao,sequencia_vitorias) VALUES (?,?,?,?,?)";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, novo.getNome());
            ps.setString(2, novo.getCategoria());
            ps.setString(3, novo.getGenero());
            ps.setInt(4, novo.getIdade());
            ps.setInt(5, novo.getSequenciaVitorias());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao inserir lutador", e);
        }
    }

    public ArrayList<CampeaoBrasileiroDTO> listar() {
        ArrayList<CampeaoBrasileiroDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM lutadores";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CampeaoBrasileiroDTO c = new CampeaoBrasileiroDTO();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCategoria(rs.getString("categoria"));
                c.setGenero(rs.getString("genero"));
                c.setIdade(rs.getInt("idade_campeao"));
                c.setSequenciaVitorias(rs.getInt("sequencia_vitorias"));

                lista.add(c);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar lutadores", e);
        }

        return lista;
    }

    public void atualizar(CampeaoBrasileiroDTO campeao) {
        String sql = "UPDATE lutadores SET nome=?, categoria=?, genero=?, idade_campeao=?, sequencia_vitorias=? WHERE id=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, campeao.getNome());
            ps.setString(2, campeao.getCategoria());
            ps.setString(3, campeao.getGenero());
            ps.setInt(4, campeao.getIdade());
            ps.setInt(5, campeao.getSequenciaVitorias());
            ps.setInt(6, campeao.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar lutador", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM lutadores WHERE id=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao deletar lutador", e);
        }
    }
}