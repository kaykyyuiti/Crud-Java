package com.template.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.template.model.dto.CampeaoBrasileiroDTO;
import com.template.service.CampeaoBrasileiroService;
import com.template.util.DialogUtil;
import com.template.util.TextFieldUtil;
import com.template.validator.ILutadorValidator;

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

    private ILutadorValidator lutadorValidator;

    public MainController() {
    }

    public MainController(ILutadorValidator lutadorValidator) {
        this.lutadorValidator = lutadorValidator;
    }

    public void setLutadorValidator(ILutadorValidator lutadorValidator) {
        this.lutadorValidator = lutadorValidator;
    }

    @FXML private Button btnAdicionar;
    @FXML private Button btnPesquisar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAtualizar;
    @FXML private Button btnLimpar;

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSequencia;
    @FXML private TextField txtPesquisar;

    @FXML private ComboBox<String> cbCategoria;
    @FXML private ComboBox<String> cbGenero;

    @FXML private TableView<CampeaoBrasileiroDTO> tblLutador;
    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colId;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colNome;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colCategoria;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colGenero;
    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colIdade;
    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colSequencia;

    private final CampeaoBrasileiroService campeaoBrasileiroService = new CampeaoBrasileiroService();

    @Override
    public void initialize(URL url, ResourceBundle recursosInterface) {
        configurarColunas();
        configurarCombos();

        // Corrigido: Chamando um por um conforme a assinatura do seu TextFieldUtil
        TextFieldUtil.permitirSomenteNumeros(txtIdade);
        TextFieldUtil.permitirSomenteNumeros(txtSequencia);

        configurarListenerSelecaoTabela();
        resetarTela();
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        if (lutadorValidator != null && lutadorValidator.validarLutador(
                txtNome.getText().trim(),
                cbCategoria.getValue() != null ? cbCategoria.getValue() : "",
                cbGenero.getValue() != null ? cbGenero.getValue() : "",
                txtIdade.getText().trim(),
                txtSequencia.getText().trim())) {

            if (DialogUtil.mostrarConfirmacao("Adicionar Lutador", "Deseja realmente cadastrar este lutador?")) {
                try {
                    CampeaoBrasileiroDTO lutador = obterDadosTela();
                    campeaoBrasileiroService.cadastrar(lutador);
                    DialogUtil.mostrarInformacao("Sucesso", "Lutador cadastrado com sucesso!");
                    resetarTela();
                } catch (Exception e) {
                    DialogUtil.mostrarErro("Erro ao Cadastrar", "Falha ao cadastrar: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty() && lutadorValidator != null && lutadorValidator.validarLutador(
                txtNome.getText().trim(),
                cbCategoria.getValue() != null ? cbCategoria.getValue() : "",
                cbGenero.getValue() != null ? cbGenero.getValue() : "",
                txtIdade.getText().trim(),
                txtSequencia.getText().trim())) {

            if (DialogUtil.mostrarConfirmacao("Atualizar Lutador", "Deseja salvar as alterações deste lutador?")) {
                try {
                    CampeaoBrasileiroDTO lutador = obterDadosTela();
                    campeaoBrasileiroService.atualizar(lutador);
                    DialogUtil.mostrarInformacao("Sucesso", "Lutador atualizado com sucesso!");
                    resetarTela();
                } catch (Exception e) {
                    DialogUtil.mostrarErro("Erro ao Atualizar", "Falha ao atualizar: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {

            CampeaoBrasileiroDTO lutadorSelecionado = tblLutador.getSelectionModel().getSelectedItem();
            String nomeLutador = lutadorSelecionado != null ? lutadorSelecionado.getNome() : "este lutador";

            if (DialogUtil.mostrarConfirmacao("Excluir Lutador", "Deseja realmente excluir:\n\n" + nomeLutador + "?")) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    campeaoBrasileiroService.excluir(id);
                    DialogUtil.mostrarInformacao("Sucesso", "Lutador excluído com sucesso!");
                    resetarTela();
                } catch (Exception e) {
                    DialogUtil.mostrarErro("Erro ao Excluir", "Falha ao excluir: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    private void btnPesquisarAction(ActionEvent event) {
        try {
            List<CampeaoBrasileiroDTO> resultado = campeaoBrasileiroService.pesquisarPorNome(txtPesquisar.getText());
            tblLutador.setItems(FXCollections.observableArrayList(resultado));

            DialogUtil.mostrarInformacao("Pesquisa", resultado.size() + " lutador(es) encontrado(s).");

            if (resultado.isEmpty()) {
                limparFormulario();
            }
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro na Pesquisa", "Falha ao realizar a busca: " + e.getMessage());
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        resetarTela();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colSequencia.setCellValueFactory(new PropertyValueFactory<>("sequenciaVitorias"));
        tblLutador.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurarCombos() {
        cbGenero.getItems().setAll("Masculino", "Feminino");
        cbCategoria.getItems().setAll(
                "Peso Mosca", "Peso Galo", "Peso Pena", "Peso Leve",
                "Peso Meio-Médio", "Peso Médio", "Peso Meio-Pesado", "Peso Pesado"
        );
    }

    private void configurarListenerSelecaoTabela() {
        tblLutador.getSelectionModel().selectedItemProperty().addListener((obs, antigo, lutador) -> {
            if (lutador != null) {
                txtId.setText(String.valueOf(lutador.getId()));
                txtNome.setText(lutador.getNome());
                cbCategoria.setValue(lutador.getCategoria());
                cbGenero.setValue(lutador.getGenero());
                txtIdade.setText(String.valueOf(lutador.getIdade()));
                txtSequencia.setText(String.valueOf(lutador.getSequenciaVitorias()));

                btnAtualizar.setDisable(false);
                btnExcluir.setDisable(false);
            } else {
                desabilitarBotoesEdicao();
            }
        });
    }

    private void carregarTabela() {
        tblLutador.setItems(FXCollections.observableArrayList(campeaoBrasileiroService.listar()));
    }

    private void resetarTela() {
        carregarTabela();
        limparFormulario();
    }

    private void limparFormulario() {
        txtId.clear();
        txtNome.clear();
        txtIdade.clear();
        txtSequencia.clear();
        txtPesquisar.clear();

        cbCategoria.setValue(null);
        cbGenero.setValue(null);

        tblLutador.getSelectionModel().clearSelection();
        desabilitarBotoesEdicao();
        txtNome.requestFocus();
    }

    private void desabilitarBotoesEdicao() {
        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    private CampeaoBrasileiroDTO obterDadosTela() {
        CampeaoBrasileiroDTO lutador = new CampeaoBrasileiroDTO();

        if (!txtId.getText().isEmpty()) {
            lutador.setId(Integer.parseInt(txtId.getText()));
        }

        lutador.setNome(txtNome.getText().trim());
        lutador.setCategoria(cbCategoria.getValue());
        lutador.setGenero(cbGenero.getValue());
        lutador.setIdade(Integer.parseInt(txtIdade.getText().trim()));
        lutador.setSequenciaVitorias(Integer.parseInt(txtSequencia.getText().trim()));

        return lutador;
    }
}