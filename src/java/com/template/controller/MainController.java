package com.template.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.template.model.dto.CampeaoBrasileiroDTO;
import com.template.service.CampeaoBrasileiroService;
import com.template.util.DialogUtil;
import com.template.util.TextFieldUtil;
import com.template.validator.LutadorValidator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private final CampeaoBrasileiroService campeaoBrasileiroService =
            new CampeaoBrasileiroService();

    @Override
    public void initialize(URL url, ResourceBundle recursosInterface) {

        configurarTabela();
        configurarCombos();
        configurarCamposNumericos();
        configurarBotoes();
        configurarSelecaoTabela();

        carregarLutadores();
    }

    private void configurarTabela() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria")
        );

        colGenero.setCellValueFactory(
                new PropertyValueFactory<>("genero")
        );

        colIdade.setCellValueFactory(
                new PropertyValueFactory<>("idade")
        );

        colSequencia.setCellValueFactory(
                new PropertyValueFactory<>("sequenciaVitorias")
        );
    }

    private void configurarCombos() {

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
    }

    private void configurarCamposNumericos() {

        TextFieldUtil.permitirSomenteNumeros(txtIdade);
        TextFieldUtil.permitirSomenteNumeros(txtSequencia);
    }

    private void configurarBotoes() {

        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    private void configurarSelecaoTabela() {

        tblLutador.getSelectionModel()
                .selectedItemProperty()
                .addListener((observavel, antigo, novo) -> carregarCampos());
    }

    @FXML
    private void btnAdicionarAction(ActionEvent evento) {

        if (!DialogUtil.mostrarConfirmacao(
                "Adicionar Lutador",
                "Deseja realmente cadastrar este lutador?")) {
            return;
        }

        CampeaoBrasileiroDTO lutador = obterDadosTela();

        if (lutador == null) {
            return;
        }

        try {

            campeaoBrasileiroService.cadastrar(lutador);

            carregarLutadores();
            limparCampos();

            DialogUtil.mostrarInformacao(
                    "Sucesso",
                    "Lutador cadastrado com sucesso!"
            );

        } catch (IllegalArgumentException erro) {

            DialogUtil.mostrarErro(
                    "Lutador Duplicado",
                    erro.getMessage()
            );
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent evento) {

        if (!DialogUtil.mostrarConfirmacao(
                "Atualizar Lutador",
                "Deseja salvar as alterações deste lutador?")) {
            return;
        }

        CampeaoBrasileiroDTO lutador = obterDadosTela();

        if (lutador == null) {
            return;
        }

        campeaoBrasileiroService.atualizar(lutador);

        carregarLutadores();
        limparCampos();

        DialogUtil.mostrarInformacao(
                "Sucesso",
                "Lutador atualizado com sucesso!"
        );
    }

    @FXML
    private void btnExcluirAction(ActionEvent evento) {

        if (!LutadorValidator.validarSelecaoParaExcluir(
                txtId.getText())) {
            return;
        }

        CampeaoBrasileiroDTO lutadorSelecionado =
                tblLutador.getSelectionModel().getSelectedItem();

        String nomeLutador = lutadorSelecionado != null
                ? lutadorSelecionado.getNome()
                : "este lutador";

        if (!DialogUtil.mostrarConfirmacao(
                "Excluir Lutador",
                "Deseja realmente excluir:\n\n" + nomeLutador + "?")) {
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        campeaoBrasileiroService.excluir(id);

        carregarLutadores();
        limparCampos();

        DialogUtil.mostrarInformacao(
                "Sucesso",
                "Lutador excluído com sucesso!"
        );
    }

    @FXML
    private void btnLimparAction(ActionEvent evento) {

        if (!DialogUtil.mostrarConfirmacao(
                "Limpar Campos",
                "Deseja realmente limpar todos os campos?")) {
            return;
        }

        limparCampos();
        carregarLutadores();
    }

    @FXML
    private void btnPesquisarAction(ActionEvent evento) {

        ArrayList<CampeaoBrasileiroDTO> lutadoresEncontrados =
                campeaoBrasileiroService.pesquisarPorNome(
                        txtPesquisar.getText()
                );

        tblLutador.setItems(
                FXCollections.observableArrayList(
                        lutadoresEncontrados
                )
        );

        DialogUtil.mostrarInformacao(
                "Pesquisa",
                lutadoresEncontrados.size()
                        + " lutador(es) encontrado(s)."
        );
    }

    private void carregarLutadores() {

        ArrayList<CampeaoBrasileiroDTO> lutadores =
                campeaoBrasileiroService.listar();

        tblLutador.setItems(
                FXCollections.observableArrayList(lutadores)
        );
    }

    private void carregarCampos() {

        CampeaoBrasileiroDTO lutadorSelecionado =
                tblLutador.getSelectionModel().getSelectedItem();

        if (lutadorSelecionado == null) {
            return;
        }

        txtId.setText(
                String.valueOf(lutadorSelecionado.getId())
        );

        txtNome.setText(
                lutadorSelecionado.getNome()
        );

        cbCategoria.setValue(
                lutadorSelecionado.getCategoria()
        );

        cbGenero.setValue(
                lutadorSelecionado.getGenero()
        );

        txtIdade.setText(
                String.valueOf(lutadorSelecionado.getIdade())
        );

        txtSequencia.setText(
                String.valueOf(
                        lutadorSelecionado.getSequenciaVitorias()
                )
        );

        btnAtualizar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private CampeaoBrasileiroDTO obterDadosTela() {

        String nome = txtNome.getText().trim();

        String categoria = cbCategoria.getValue() != null
                ? cbCategoria.getValue()
                : "";

        String genero = cbGenero.getValue() != null
                ? cbGenero.getValue()
                : "";

        String idade = txtIdade.getText().trim();

        String sequencia = txtSequencia.getText().trim();

        if (!LutadorValidator.validarLutador(
                nome,
                categoria,
                genero,
                idade,
                sequencia)) {
            return null;
        }

        CampeaoBrasileiroDTO lutador =
                new CampeaoBrasileiroDTO();

        if (!txtId.getText().isEmpty()) {
            lutador.setId(
                    Integer.parseInt(txtId.getText())
            );
        }

        lutador.setNome(nome);
        lutador.setCategoria(categoria);
        lutador.setGenero(genero);
        lutador.setIdade(
                Integer.parseInt(idade)
        );
        lutador.setSequenciaVitorias(
                Integer.parseInt(sequencia)
        );

        return lutador;
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
}