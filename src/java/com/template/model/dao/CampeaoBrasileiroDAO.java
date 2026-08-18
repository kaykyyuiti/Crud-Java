package com.template.model.dao;

import com.template.model.dto.CampeaoBrasileiroDTO;
import com.template.model.Conexao;
import com.template.util.DialogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CampeaoBrasileiroDAO {

    private static final Logger logger = Logger.getLogger(CampeaoBrasileiroDAO.class.getName());

    public void inserir(CampeaoBrasileiroDTO lutadorParaInserir) {

        String instrucaoSqlInserir = "INSERT INTO lutadores (nome, categoria, genero, idade_campeao, sequencia_vitorias) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexaoBancoDados = Conexao.obterConexao();
             PreparedStatement comandoSqlPreparado = conexaoBancoDados.prepareStatement(instrucaoSqlInserir)) {

            comandoSqlPreparado.setString(1, lutadorParaInserir.getNome());
            comandoSqlPreparado.setString(2, lutadorParaInserir.getCategoria());
            comandoSqlPreparado.setString(3, lutadorParaInserir.getGenero());
            comandoSqlPreparado.setInt(4, lutadorParaInserir.getIdade());
            comandoSqlPreparado.setInt(5, lutadorParaInserir.getSequenciaVitorias());

            comandoSqlPreparado.executeUpdate();

        } catch (SQLException excecaoBancoDados) {
            logger.log(Level.SEVERE, "Erro ao inserir lutador.", excecaoBancoDados);
            DialogUtil.mostrarErro("Erro no Banco de Dados", "Falha ao Cadastrar Lutador\n" + excecaoBancoDados.getMessage());
        }
    }

    public ArrayList<CampeaoBrasileiroDTO> listar() {

        ArrayList<CampeaoBrasileiroDTO> listaLutadoresEncontrados = new ArrayList<>();

        String instrucaoSqlSelecao = "SELECT * FROM lutadores";

        try (Connection conexaoBancoDados = Conexao.obterConexao();
             PreparedStatement comandoSqlPreparado = conexaoBancoDados.prepareStatement(instrucaoSqlSelecao);
             ResultSet resultadoConsultaBanco = comandoSqlPreparado.executeQuery()) {

            while (resultadoConsultaBanco.next()) {

                CampeaoBrasileiroDTO lutadorMapeado = new CampeaoBrasileiroDTO();

                lutadorMapeado.setId(resultadoConsultaBanco.getInt("id"));
                lutadorMapeado.setNome(resultadoConsultaBanco.getString("nome"));
                lutadorMapeado.setCategoria(resultadoConsultaBanco.getString("categoria"));
                lutadorMapeado.setGenero(resultadoConsultaBanco.getString("genero"));
                lutadorMapeado.setIdade(resultadoConsultaBanco.getInt("idade_campeao"));
                lutadorMapeado.setSequenciaVitorias(resultadoConsultaBanco.getInt("sequencia_vitorias"));

                listaLutadoresEncontrados.add(lutadorMapeado);
            }

        } catch (SQLException excecaoBancoDados) {
            logger.log(Level.SEVERE, "Erro ao listar lutadores.", excecaoBancoDados);
            DialogUtil.mostrarErro("Erro no Banco de Dados", "Falha ao Carregar Lista\n" + excecaoBancoDados.getMessage());
        }

        return listaLutadoresEncontrados;
    }

    public void atualizar(CampeaoBrasileiroDTO lutadorParaAtualizar) {

        String instrucaoSqlAtualizar = "UPDATE lutadores SET nome = ?, categoria = ?, genero = ?, idade_campeao = ?, sequencia_vitorias = ? WHERE id = ?";

        try (Connection conexaoBancoDados = Conexao.obterConexao();
             PreparedStatement comandoSqlPreparado = conexaoBancoDados.prepareStatement(instrucaoSqlAtualizar)) {

            comandoSqlPreparado.setString(1, lutadorParaAtualizar.getNome());
            comandoSqlPreparado.setString(2, lutadorParaAtualizar.getCategoria());
            comandoSqlPreparado.setString(3, lutadorParaAtualizar.getGenero());
            comandoSqlPreparado.setInt(4, lutadorParaAtualizar.getIdade());
            comandoSqlPreparado.setInt(5, lutadorParaAtualizar.getSequenciaVitorias());
            comandoSqlPreparado.setInt(6, lutadorParaAtualizar.getId());

            comandoSqlPreparado.executeUpdate();

        } catch (SQLException excecaoBancoDados) {
            logger.log(Level.SEVERE, "Erro ao atualizar lutador.", excecaoBancoDados);
            DialogUtil.mostrarErro("Erro no Banco de Dados", "Falha ao Atualizar Lutador\n" + excecaoBancoDados.getMessage());
        }
    }

    public void deletar(int idLutadorParaExcluir) {

        String instrucaoSqlDeletar = "DELETE FROM lutadores WHERE id = ?";

        try (Connection conexaoBancoDados = Conexao.obterConexao();
             PreparedStatement comandoSqlPreparado = conexaoBancoDados.prepareStatement(instrucaoSqlDeletar)) {

            comandoSqlPreparado.setInt(1, idLutadorParaExcluir);

            comandoSqlPreparado.executeUpdate();

        } catch (SQLException excecaoBancoDados) {
            logger.log(Level.SEVERE, "Erro ao excluir lutador.", excecaoBancoDados);
            DialogUtil.mostrarErro("Erro no Banco de Dados", "Falha ao Excluir Lutador\n" + excecaoBancoDados.getMessage());
        }
    }
}