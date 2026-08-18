package com.template.validator;

public class NumeroValidator {

    public static boolean validarNumero(String texto) {

        if (texto == null || texto.isEmpty()) {
            return false;
        }

        return texto.matches("\\d*");
    }
}