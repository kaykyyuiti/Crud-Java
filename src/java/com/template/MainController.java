package com.template;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtIdade;
    @FXML private TextField txtSequencia;
    @FXML private TextField txtPesquisar;

    @FXML private ComboBox<String> cbCategoria;
    @FXML private ComboBox<String> cbGenero;

    @FXML private Label lblMensagem;
    @FXML private Label lblTotal;

    @FXML private Button btnAtualizar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAdicionar;
    @FXML private Button btnLimpar;

    @FXML private TableView<CampeaoBrasileiroDTO> tblLutador;

    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colId;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colNome;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colCategoria;
    @FXML private TableColumn<CampeaoBrasileiroDTO, String> colGenero;
    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colIdade;
    @FXML private TableColumn<CampeaoBrasileiroDTO, Integer> colSequencia;

    private final CampeaoBrasileiroDAO dao = new CampeaoBrasileiroDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colSequencia.setCellValueFactory(new PropertyValueFactory<>("sequenciaVitorias"));

        cbGenero.getItems().addAll("Masculino", "Feminino");

        cbCategoria.getItems().addAll(
                "Peso Mosca", "Peso Galo", "Peso Pena",
                "Peso Leve", "Peso Meio-Médio",
                "Peso Médio", "Peso Meio-Pesado", "Peso Pesado"
        );

        permitirSomenteNumeros(txtIdade);
        permitirSomenteNumeros(txtSequencia);

        btnAtualizar.setDisable(true);
        btnExcluir.setDisable(true);

        carregarLutadores();
    }

    private boolean confirmar(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(titulo);
        a.setHeaderText("Confirmação necessária");
        a.setContentText(msg);

        return a.showAndWait().isPresent()
                && a.getResult() == ButtonType.OK;
    }

    @FXML
    private void btnAdicionarAction(ActionEvent e) {

        if (!confirmar("Adicionar Lutador",
                "Deseja realmente cadastrar este lutador?")) {
            lblMensagem.setText("Cadastro cancelado.");
            return;
        }

        CampeaoBrasileiroDTO novo = obterDadosTela();

        ArrayList<CampeaoBrasileiroDTO> lista = dao.listar();

        for (CampeaoBrasileiroDTO c : lista) {
            if (c.getNome().equalsIgnoreCase(novo.getNome())
                    && c.getCategoria().equalsIgnoreCase(novo.getCategoria())) {

                lblMensagem.setText("Lutador já existe nesta categoria!");
                return;
            }
        }

        dao.inserir(novo);
        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador cadastrado com sucesso!");
    }

    @FXML
    private void btnAtualizarAction(ActionEvent e) {

        if (!confirmar("Atualizar Lutador",
                "Deseja salvar as alterações deste lutador?")) {
            lblMensagem.setText("Atualização cancelada.");
            return;
        }

        dao.atualizar(obterDadosTela());
        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador atualizado com sucesso!");
    }

    @FXML
    private void btnExcluirAction(ActionEvent e) {

        if (txtId.getText().isEmpty()) {
            lblMensagem.setText("Selecione um lutador.");
            return;
        }

        CampeaoBrasileiroDTO c = tblLutador.getSelectionModel().getSelectedItem();
        String nome = (c != null) ? c.getNome() : "este lutador";

        if (!confirmar("Excluir Lutador",
                "Deseja realmente excluir:\n\n" + nome)) {
            lblMensagem.setText("Exclusão cancelada.");
            return;
        }

        dao.deletar(Integer.parseInt(txtId.getText()));
        carregarLutadores();
        limparCampos();

        lblMensagem.setText("Lutador excluído com sucesso!");
    }

    @FXML
    private void btnLimparAction(ActionEvent e) {

        if (!confirmar("Limpar Campos",
                "Deseja realmente limpar todos os campos?")) {
            lblMensagem.setText("Limpeza cancelada.");
            return;
        }

        limparCampos();
        carregarLutadores();

        lblMensagem.setText("Campos limpos.");
    }

    @FXML
    private void btnPesquisarAction(ActionEvent e) {

        String p = txtPesquisar.getText().toLowerCase();

        ArrayList<CampeaoBrasileiroDTO> lista = dao.listar();
        ArrayList<CampeaoBrasileiroDTO> filtrada = new ArrayList<>();

        for (CampeaoBrasileiroDTO c : lista) {
            if (c.getNome().toLowerCase().contains(p)) {
                filtrada.add(c);
            }
        }

        tblLutador.setItems(FXCollections.observableArrayList(filtrada));
        lblMensagem.setText(filtrada.size() + " lutador(es) encontrado(s).");
    }

    @FXML
    private void carregarLutadores() {

        ArrayList<CampeaoBrasileiroDTO> lista = dao.listar();
        tblLutador.setItems(FXCollections.observableArrayList(lista));

        lblTotal.setText("Total de lutadores: " + lista.size());
    }

    @FXML
    private void carregarCampos() {

        CampeaoBrasileiroDTO c = tblLutador.getSelectionModel().getSelectedItem();

        if (c != null) {

            txtId.setText(String.valueOf(c.getId()));
            txtNome.setText(c.getNome());
            cbCategoria.setValue(c.getCategoria());
            cbGenero.setValue(c.getGenero());
            txtIdade.setText(String.valueOf(c.getIdade()));
            txtSequencia.setText(String.valueOf(c.getSequenciaVitorias()));

            btnAtualizar.setDisable(false);
            btnExcluir.setDisable(false);
        }
    }

    private CampeaoBrasileiroDTO obterDadosTela() {

        CampeaoBrasileiroDTO c = new CampeaoBrasileiroDTO();

        if (!txtId.getText().isEmpty()) {
            c.setId(Integer.parseInt(txtId.getText()));
        }

        c.setNome(txtNome.getText());
        c.setCategoria(cbCategoria.getValue());
        c.setGenero(cbGenero.getValue());
        c.setIdade(Integer.parseInt(txtIdade.getText()));
        c.setSequenciaVitorias(Integer.parseInt(txtSequencia.getText()));

        return c;
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

    private void permitirSomenteNumeros(TextField campo) {

        campo.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*")) {
                campo.setText(newV.replaceAll("[^\\d]", ""));
            }
        });
    }
}