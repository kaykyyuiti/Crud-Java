package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CampeaoBrasileiroDAO {

    public void inserir(CampeaoBrasileiroDTO novo) {

        String sql = "INSERT INTO lutadores (nome, categoria, genero, idade_campeao, sequencia_vitorias) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, novo.getNome());
            ps.setString(2, novo.getCategoria());
            ps.setString(3, novo.getGenero());
            ps.setInt(4, novo.getIdade());
            ps.setInt(5, novo.getSequenciaVitorias());

            ps.execute();

        } catch (SQLException e) {

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

        }

        return lista;
    }

    public void atualizar(CampeaoBrasileiroDTO atualizar) {

        String sql = "UPDATE lutadores SET nome=?, categoria=?, genero=?, idade_campeao=?, sequencia_vitorias=? WHERE id=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, atualizar.getNome());
            ps.setString(2, atualizar.getCategoria());
            ps.setString(3, atualizar.getGenero());
            ps.setInt(4, atualizar.getIdade());
            ps.setInt(5, atualizar.getSequenciaVitorias());
            ps.setInt(6, atualizar.getId());

            ps.execute();

        } catch (SQLException e) {

        }
    }

    public void deletar(int id) {

        String sql = "DELETE FROM lutadores WHERE id=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.execute();

        } catch (SQLException e) {

        }
    }

    public CampeaoBrasileiroDTO buscarPorId(int idBusca) {

        String sql = "SELECT * FROM lutadores WHERE id=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBusca);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                CampeaoBrasileiroDTO c = new CampeaoBrasileiroDTO();

                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCategoria(rs.getString("categoria"));
                c.setGenero(rs.getString("genero"));
                c.setIdade(rs.getInt("idade_campeao"));
                c.setSequenciaVitorias(rs.getInt("sequencia_vitorias"));

                return c;
            }

        } catch (SQLException e) {

        }

        return null;
    }
}