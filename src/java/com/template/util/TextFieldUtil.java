package com.template.util;

import javafx.scene.control.TextField;

public class TextFieldUtil {

    public static void permitirSomenteNumeros(TextField campo) {

        campo.textProperty().addListener((observavel, valorAnterior, novoValor) -> {

            if (!novoValor.matches("\\d*")) {
                campo.setText(novoValor.replaceAll("[^\\d]", ""));
            }
        });
    }
}