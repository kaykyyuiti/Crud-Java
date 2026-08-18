package com.template.service;

import java.util.ArrayList;

import com.template.model.dao.CampeaoBrasileiroDAO;
import com.template.model.dto.CampeaoBrasileiroDTO;

public class CampeaoBrasileiroService {

    private final CampeaoBrasileiroDAO campeaoBrasileiroDao;

    public CampeaoBrasileiroService() {
        campeaoBrasileiroDao = new CampeaoBrasileiroDAO();
    }

    public ArrayList<CampeaoBrasileiroDTO> listar() {
        return campeaoBrasileiroDao.listar();
    }

    public void cadastrar(CampeaoBrasileiroDTO lutador) {

        if (existeLutador(lutador)) {
            throw new IllegalArgumentException(
                    "O lutador " + lutador.getNome()
                            + " já está cadastrado nesta categoria."
            );
        }

        campeaoBrasileiroDao.inserir(lutador);
    }

    public void atualizar(CampeaoBrasileiroDTO lutador) {
        campeaoBrasileiroDao.atualizar(lutador);
    }

    public void excluir(int id) {
        campeaoBrasileiroDao.deletar(id);
    }

    public ArrayList<CampeaoBrasileiroDTO> pesquisarPorNome(String nome) {

        ArrayList<CampeaoBrasileiroDTO> lutadoresEncontrados = new ArrayList<>();

        String nomePesquisa = nome == null ? "" : nome.trim().toLowerCase();

        for (CampeaoBrasileiroDTO lutador : campeaoBrasileiroDao.listar()) {

            if (lutador.getNome().toLowerCase().contains(nomePesquisa)) {
                lutadoresEncontrados.add(lutador);
            }
        }

        return lutadoresEncontrados;
    }

    private boolean existeLutador(CampeaoBrasileiroDTO lutadorParaCadastrar) {

        for (CampeaoBrasileiroDTO lutadorExistente : campeaoBrasileiroDao.listar()) {

            boolean mesmoNome = lutadorExistente.getNome()
                    .equalsIgnoreCase(lutadorParaCadastrar.getNome());

            boolean mesmaCategoria = lutadorExistente.getCategoria()
                    .equalsIgnoreCase(lutadorParaCadastrar.getCategoria());

            if (mesmoNome && mesmaCategoria) {
                return true;
            }
        }

        return false;
    }
}