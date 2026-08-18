package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogUtil {

    public static boolean mostrarConfirmacao(String titulo, String mensagem) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(titulo);
        alert.setHeaderText("Confirmação necessária");
        alert.setContentText(mensagem);

        return alert.showAndWait().isPresent()
                && alert.getResult() == ButtonType.OK;
    }

    public static void mostrarErro(String titulo, String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(titulo);
        alert.setHeaderText("Atenção");
        alert.setContentText(mensagem);

        alert.showAndWait();
    }

    public static void mostrarInformacao(String titulo, String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

        alert.showAndWait();
    }
}