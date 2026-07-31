package com.template.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.template.model.dao.CampeaoBrasileiroDAO;
import com.template.model.dto.CampeaoBrasileiroDTO;
import com.template.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtSequencia;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private ComboBox<String> cbCategoria;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private Label lblMensagem;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnLimpar;

    @FXML
    private TableView<CampeaoBrasileiroDTO> tblLutador;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, Integer> colId;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, String> colNome;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, String> colCategoria;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, String> colGenero;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, Integer> colIdade;

    @FXML
    private TableColumn<CampeaoBrasileiroDTO, Integer> colSequencia;

    private final CampeaoBrasileiroDAO campeaoBrasileiroDao = new CampeaoBrasileiroDAO();

    @Override
    public void initialize(URL urlConexaoFxml, ResourceBundle recursosInterface) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colSequencia.setCellValueFactory(new PropertyValueFactory<>("sequenciaVitorias"));

        cbGenero.getItems().addAll(
                "Masculino",
                "Feminino"
        );

        cbCategoria.getItems().addAll(
                "Peso Mosca",
                "Peso Galo",
                "Peso Pena",
                "Peso Leve",
                "Peso Meio-Médio",
                "Peso Médio",
                "Peso Meio-Pesado",
                "Peso Pesado"
        );

        permitirSomenteNumeros(txtIdade);
        permitirSomenteNumeros(txtSequencia);

        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);

        carregarLutadores();
    }

    @FXML
    private void btnAdicionarAction(ActionEvent eventoBotaoAdicionar) {

        if (!DialogUtil.mostrarConfirmacao(
                "Adicionar Lutador",
                "Deseja realmente cadastrar este lutador?")) {

            lblMensagem.setText("Cadastro cancelado.");
            return;
        }

        CampeaoBrasileiroDTO lutadorParaCadastrar = obterDadosTela();
        if (lutadorParaCadastrar == null) {
            return;
        }

        ArrayList<CampeaoBrasileiroDTO> listaLutadoresCadastrados = campeaoBrasileiroDao.listar();

        for (CampeaoBrasileiroDTO lutadorExistente : listaLutadoresCadastrados) {
            if (lutadorExistente.getNome().equalsIgnoreCase(lutadorParaCadastrar.getNome())
                    && lutadorExistente.getCategoria().equalsIgnoreCase(lutadorParaCadastrar.getCategoria())) {

                DialogUtil.mostrarErro("Lutador Duplicado", "O lutador " + lutadorParaCadastrar.getNome() + " já está cadastrado nesta categoria!");
                lblMensagem.setText("Lutador já existe nesta categoria!");
                return;
            }
        }

        campeaoBrasileiroDao.inserir(lutadorParaCadastrar);

        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador cadastrado com sucesso!");
        DialogUtil.mostrarInformacao("Sucesso", "Lutador cadastrado com sucesso!");
    }

    @FXML
    private void btnAtualizarAction(ActionEvent eventoBotaoAtualizar) {

        if (!DialogUtil.mostrarConfirmacao(
                "Atualizar Lutador",
                "Deseja salvar as alterações deste lutador?")) {

            lblMensagem.setText("Atualização cancelada.");
            return;
        }

        CampeaoBrasileiroDTO lutadorParaAtualizar = obterDadosTela();
        if (lutadorParaAtualizar == null) {
            return;
        }

        campeaoBrasileiroDao.atualizar(lutadorParaAtualizar);

        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador atualizado com sucesso!");
        DialogUtil.mostrarInformacao("Sucesso", "Lutador atualizado com sucesso!");
    }

    @FXML
    private void btnExcluirAction(ActionEvent eventoBotaoExcluir) {

        if (txtId.getText().isEmpty()) {
            DialogUtil.mostrarErro("Seleção Inválida", "Selecione um lutador na tabela para excluir.");
            lblMensagem.setText("Selecione um lutador.");
            return;
        }

        CampeaoBrasileiroDTO lutadorSelecionadoParaExclusao = tblLutador.getSelectionModel().getSelectedItem();

        String nomeLutadorExclusao = lutadorSelecionadoParaExclusao != null
                ? lutadorSelecionadoParaExclusao.getNome()
                : "este lutador";

        if (!DialogUtil.mostrarConfirmacao(
                "Excluir Lutador",
                "Deseja realmente excluir:\n\n" + nomeLutadorExclusao + "?")) {

            lblMensagem.setText("Exclusão cancelada.");
            return;
        }

        int idLutadorParaExcluir = Integer.parseInt(txtId.getText());
        campeaoBrasileiroDao.deletar(idLutadorParaExcluir);

        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador excluído com sucesso!");
        DialogUtil.mostrarInformacao("Sucesso", "Lutador excluído com sucesso!");
    }

    @FXML
    private void btnLimparAction(ActionEvent eventoBotaoLimpar) {

        if (!DialogUtil.mostrarConfirmacao(
                "Limpar Campos",
                "Deseja realmente limpar todos os campos?")) {

            lblMensagem.setText("Limpeza cancelada.");
            return;
        }

        limparCampos();
        carregarLutadores();

        lblMensagem.setText("Campos limpos.");
    }

    @FXML
    private void btnPesquisarAction(ActionEvent eventoBotaoPesquisar) {

        String textoPesquisaNome = txtPesquisar.getText().toLowerCase();

        ArrayList<CampeaoBrasileiroDTO> listaTodosLutadores = campeaoBrasileiroDao.listar();
        ArrayList<CampeaoBrasileiroDTO> listaLutadoresFiltrados = new ArrayList<>();

        for (CampeaoBrasileiroDTO lutadorDaLista : listaTodosLutadores) {
            if (lutadorDaLista.getNome().toLowerCase().contains(textoPesquisaNome)) {
                listaLutadoresFiltrados.add(lutadorDaLista);
            }
        }

        tblLutador.setItems(FXCollections.observableArrayList(listaLutadoresFiltrados));

        lblMensagem.setText(listaLutadoresFiltrados.size() + " lutador(es) encontrado(s).");
    }

    @FXML
    private void carregarLutadores() {

        ArrayList<CampeaoBrasileiroDTO> listaLutadoresDoBanco = campeaoBrasileiroDao.listar();

        tblLutador.setItems(FXCollections.observableArrayList(listaLutadoresDoBanco));

        lblTotal.setText("Total de lutadores: " + listaLutadoresDoBanco.size());
    }

    @FXML
    private void carregarCampos() {

        CampeaoBrasileiroDTO lutadorSelecionadoNaTabela = tblLutador.getSelectionModel().getSelectedItem();

        if (lutadorSelecionadoNaTabela != null) {

            txtId.setText(String.valueOf(lutadorSelecionadoNaTabela.getId()));
            txtNome.setText(lutadorSelecionadoNaTabela.getNome());
            cbCategoria.setValue(lutadorSelecionadoNaTabela.getCategoria());
            cbGenero.setValue(lutadorSelecionadoNaTabela.getGenero());
            txtIdade.setText(String.valueOf(lutadorSelecionadoNaTabela.getIdade()));
            txtSequencia.setText(String.valueOf(lutadorSelecionadoNaTabela.getSequenciaVitorias()));

            btnAtualizar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }

    private CampeaoBrasileiroDTO obterDadosTela() {

        if (txtNome.getText().trim().isEmpty() || cbCategoria.getValue() == null || cbGenero.getValue() == null) {
            DialogUtil.mostrarErro("Campos Obrigatórios", "Por favor, preencha Nome, Categoria e Gênero.");
            return null;
        }

        CampeaoBrasileiroDTO lutadorConstruidoDaTela = new CampeaoBrasileiroDTO();

        if (!txtId.getText().isEmpty()) {
            lutadorConstruidoDaTela.setId(Integer.parseInt(txtId.getText()));
        }

        lutadorConstruidoDaTela.setNome(txtNome.getText().trim());
        lutadorConstruidoDaTela.setCategoria(cbCategoria.getValue());
        lutadorConstruidoDaTela.setGenero(cbGenero.getValue());

        try {
            lutadorConstruidoDaTela.setIdade(Integer.parseInt(txtIdade.getText()));
            lutadorConstruidoDaTela.setSequenciaVitorias(Integer.parseInt(txtSequencia.getText()));
        } catch (NumberFormatException excecaoFormatoNumero) {
            DialogUtil.mostrarErro("Erro de Formatação", "Idade e Sequência de Vitórias devem ser números inteiros válidos.");
            lblMensagem.setText("Preencha Idade e Sequência corretamente.");
            return null;
        }

        return lutadorConstruidoDaTela;
    }

    private void limparCampos() {

        txtId.clear();
        txtNome.clear();
        txtIdade.clear();
        txtSequencia.clear();
        txtPesquisar.clear();

        cbCategoria.setValue(null);
        cbGenero.setValue(null);

        tblLutador.getSelectionModel().clearSelection();

        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);

        txtNome.requestFocus();
    }

    private void permitirSomenteNumeros(TextField campoTextoParaFiltrar) {

        campoTextoParaFiltrar.textProperty().addListener((propriedadeObservavel, valorAnteriorTexto, novoValorTexto) -> {
            if (!novoValorTexto.matches("\\d*")) {
                campoTextoParaFiltrar.setText(novoValorTexto.replaceAll("[^\\d]", ""));
            }
        });
    }
}