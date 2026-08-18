package com.template.validator;

public class TextoValidator {

    public static boolean validarTamanhoMinimo(String texto, int tamanhoMinimo) {

        if (texto == null) {
            return false;
        }

        return texto.length() >= tamanhoMinimo;
    }

    public static boolean validarTamanhoMaximo(String texto, int tamanhoMaximo) {

        if (texto == null) {
            return false;
        }

        return texto.length() <= tamanhoMaximo;
    }

    public static boolean validarTamanho(
            String texto,
            int tamanhoMinimo,
            int tamanhoMaximo) {

        if (texto == null) {
            return false;
        }

        return texto.length() >= tamanhoMinimo
                && texto.length() <= tamanhoMaximo;
    }
}