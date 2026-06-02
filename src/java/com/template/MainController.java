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
    @FXML private TextField txtCategoria;
    @FXML private TextField txtGenero;
    @FXML private TextField txtSequencia;

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

        carregarLutadores();
    }

    @FXML
    private void carregarLutadores() {
        ArrayList<CampeaoBrasileiroDTO> listaLutadores = dao.listar();
        tblLutador.setItems(FXCollections.observableArrayList(listaLutadores));
    }

    @FXML
    private void carregarCampos() {
        CampeaoBrasileiroDTO objLutadorDTO = tblLutador.getSelectionModel().getSelectedItem();

        if (objLutadorDTO != null) {
            txtId.setText(String.valueOf(objLutadorDTO.getId()));
            txtNome.setText(String.valueOf(objLutadorDTO.getNome()));
            txtCategoria.setText(String.valueOf(objLutadorDTO.getCategoria()));
            txtGenero.setText(String.valueOf(objLutadorDTO.getGenero()));
            txtIdade.setText(String.valueOf(objLutadorDTO.getIdade()));
            txtSequencia.setText(String.valueOf(objLutadorDTO.getSequenciaVitorias()));
        }
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        CampeaoBrasileiroDTO objLutadorDTO = obterDadosTela();

        if (txtId.getText().isEmpty()) {
            dao.inserir(objLutadorDTO);
        } else {
            dao.atualizar(objLutadorDTO);
        }

        carregarLutadores();
        limparCampos();
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        CampeaoBrasileiroDTO objLutadorDTO = obterDadosTela();
        dao.inserir(objLutadorDTO);
        carregarLutadores();
        limparCampos();
    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {
        dao.atualizar(obterDadosTela());
        carregarLutadores();
        limparCampos();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        dao.deletar(id);
        carregarLutadores();
        limparCampos();
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        tblLutador.getSelectionModel().clearSelection();
    }

    private CampeaoBrasileiroDTO obterDadosTela() {
        CampeaoBrasileiroDTO c = new CampeaoBrasileiroDTO();

        if (!txtId.getText().isEmpty()) {
            c.setId(Integer.parseInt(txtId.getText()));
        }
        c.setNome(txtNome.getText());
        c.setCategoria(txtCategoria.getText());
        c.setGenero(txtGenero.getText());
        c.setIdade(Integer.parseInt(txtIdade.getText()));
        c.setSequenciaVitorias(Integer.parseInt(txtSequencia.getText()));

        return c;
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCategoria.clear();
        txtGenero.clear();
        txtIdade.clear();
        txtSequencia.clear();
    }
}