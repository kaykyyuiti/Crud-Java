package com.template;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.print.DocFlavor;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnExcluir;
    @FXML private Button btnAtualizar;
    @FXML private Button btnAdicionar;
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

    @FXML
    private void btnSalvarAction(ActionEvent event){
        String nome = txtNome.getText();
        int id = Integer.parseInt(txtId.getText());

    }
    private void initialize()
    {
        System.out.println("FXML loaded successfully!");
    }
}
